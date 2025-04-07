package com.vongda.netbuddy.data.mediapipe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max

class PoseManager(private val context: Context, private val pointSignify: Boolean) {
    //Core for Pose mediapipe functionality
    private var poseListener: ((List<NormalizedLandmark>?) -> Unit)? = null
    private var poseLandmarker: PoseLandmarker? = null
    //To run on background thread
    private var cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    //Pose value
    private var poseName: MutableState<String> = mutableStateOf("none")

    //For detection of pose timing
    private var holdStart: Long = 0
    private var holdTarget: Long = 3000L
    private var isInPose: Boolean = false
    private var poseDetectedCallback: ((Boolean) -> Unit)? = null
    private var poseHeldTimeCallback: ((Long) -> Unit)? = null

    init {
        initPoseLandmarker()
    }

    private fun initPoseLandmarker() {
        try {
            val baseOptionsBuilder = BaseOptions.builder().setModelAssetPath("pose_landmarker_lite.task")

            val optionsBuilder = PoseLandmarker.PoseLandmarkerOptions.builder()
                .setBaseOptions(baseOptionsBuilder.build())
                .setRunningMode(RunningMode.LIVE_STREAM)
                .setMinPoseDetectionConfidence(0.5f)
                .setMinTrackingConfidence(0.5f)
                .setMinPosePresenceConfidence(0.5f)
                .setResultListener { result, _ ->
                    if (result.landmarks().isNotEmpty()) {
                        //List of retuned landmarks
                        val landmarks = result.landmarks()[0]

                        poseListener?.invoke(landmarks)

                        //If TPose signal already given to listen for point
                        if (pointSignify) {
                            detectPointSignal(landmarks)
                        } else {
                            detectTPose(landmarks)
                        }

                        trackPoseTime()
                    } else {
                        //If landmarks are empty
                        resetPoseTime()
                        poseListener?.invoke(null)
                    }
                }
                .setErrorListener { error ->
                    Log.e("", "MediaPipe pose detection error: $error")
                }

            poseLandmarker = PoseLandmarker.createFromOptions(context, optionsBuilder.build())
        } catch (e: Exception) {
            Log.e("", "Failed to initialize MediaPipe pose detection")
        }
    }

    //Pose detection functionality
    private fun detectPointSignal(landmarks: List<NormalizedLandmark>) {
        // Left arm landmarks (11: left shoulder, 13: left elbow, 15: left wrist)
        val leftShoulder = landmarks.getOrNull(11)
        val leftElbow = landmarks.getOrNull(13)
        val leftWrist = landmarks.getOrNull(15)

        // Right arm landmarks (12: right shoulder, 14: right elbow, 16: right wrist)
        val rightShoulder = landmarks.getOrNull(12)
        val rightElbow = landmarks.getOrNull(14)
        val rightWrist = landmarks.getOrNull(16)

        if (leftShoulder != null && leftElbow != null && leftWrist != null &&
            rightShoulder != null && rightElbow != null && rightWrist != null) {
            if (leftWrist.y() < leftShoulder.y() && rightWrist.y() > rightShoulder.y() + 0.2) {
                poseName.value = "left_up"
            } else if (rightWrist.y() < rightShoulder.y() && leftWrist.y() > leftShoulder.y() + 0.2) {
                poseName.value = "right_up"
            } else {
                poseName.value = "none"
            }
        }
    }

    private fun detectTPose(landmarks: List<NormalizedLandmark>) {
        // Left arm landmarks (11: left shoulder, 13: left elbow, 15: left wrist)
        val leftShoulder = landmarks.getOrNull(11)
        val leftElbow = landmarks.getOrNull(13)
        val leftWrist = landmarks.getOrNull(15)

        // Right arm landmarks (12: right shoulder, 14: right elbow, 16: right wrist)
        val rightShoulder = landmarks.getOrNull(12)
        val rightElbow = landmarks.getOrNull(14)
        val rightWrist = landmarks.getOrNull(16)

        val verticalTolerance = 0.1f // Tolerance for y-alignment (5% of image height)
        val horizontalDistanceThreshold = 0.15f // Distance from shoulder for arms to be extende

        if (leftShoulder != null && leftElbow != null && leftWrist != null &&
            rightShoulder != null && rightElbow != null && rightWrist != null) {

            // Check y-alignment (shoulders, elbows, and wrists roughly horizontal)
            val leftAligned = abs(leftShoulder.y() - leftElbow.y()) < verticalTolerance &&
                    abs(leftElbow.y() - leftWrist.y()) < verticalTolerance

            val rightAligned = abs(rightShoulder.y() - rightElbow.y()) < verticalTolerance &&
                    abs(rightElbow.y() - rightWrist.y()) < verticalTolerance

            // Check horizontal distance (arms extended)
            val leftHorizontalDistance = abs(leftShoulder.x() - leftWrist.x())
            val rightHorizontalDistance = abs(rightShoulder.x() - rightWrist.x())

            val armsExtended = leftHorizontalDistance > horizontalDistanceThreshold &&
                    rightHorizontalDistance > horizontalDistanceThreshold

            if (leftAligned && rightAligned && armsExtended) {
                poseName.value = "t_pose"
            } else {
                poseName.value = "none"
            }
        }
    }

    private fun trackPoseTime() {
        if (poseName.value != "" && poseName.value != "none")
        {
            //start pose timing
            if (!isInPose) {
                holdStart = System.currentTimeMillis()
                isInPose = true
                poseDetectedCallback?.invoke(true)
            } else {
                val holdTime = System.currentTimeMillis() - holdStart
                poseHeldTimeCallback?.invoke(holdTime)

                //Check if pose is held long enough
                if (holdTime >= holdTarget) {
                    poseDetectedCallback?.invoke(true)
                }
            }
        } else if (isInPose) {
            resetPoseTime()
        }
    }

    private fun resetPoseTime() {
        isInPose = false
        holdStart = 0
        poseDetectedCallback?.invoke(false)
        poseHeldTimeCallback?.invoke(0)
    }

    //Set all Listener functions
    fun setPoseListener(callback: (List<NormalizedLandmark>?) -> Unit) {
        poseListener = callback
    }

    fun setOnPoseDetectedListener(callback: (Boolean) -> Unit) {
        poseDetectedCallback = callback
    }

    fun setOnPoseHeldTimeListener(callback: (Long) -> Unit) {
        poseHeldTimeCallback = callback
    }

    //Helpers
    fun processImageProxy(imageProxy: ImageProxy) {
        val frameTime = SystemClock.uptimeMillis()
        val scale = 500f / max(imageProxy.width, imageProxy.height)

        val bitmapBuffer = imageProxy.toBitmap()

        val matrix = Matrix().apply {
            // Rotate the frame received from the camera to be in the same direction as it'll be shown
            postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())

            // flip image if user use front camera
            postScale(scale, scale)
        }

        val rotatedBitmap = Bitmap.createBitmap(
            bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height,
            matrix, true
        )

        imageProxy.close();

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()

        poseLandmarker?.detectAsync(mpImage, frameTime);
    }

    fun getCurrentPoseName(): State<String> = poseName

    fun shutdown() {
        cameraExecutor.shutdown()
        poseLandmarker?.close()
    }
}
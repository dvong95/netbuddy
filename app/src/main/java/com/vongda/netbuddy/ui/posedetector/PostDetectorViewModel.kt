package com.vongda.netbuddy.ui.posetrainer

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.AndroidViewModel
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.google.mlkit.vision.pose.PoseLandmark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max

data class PoseTrainerUiState(
    var currentPose: String = ""
)

class PostTrainerViewModel(application: Application): AndroidViewModel(application) {
    private var tPoseStartTime: Long = 0L
    private var isTPoseDetected = false
    private val requiredHoldTime = 1000L

    private var gestureJob: Job? = null
    private val gestureTimeout = 5000L
    private var detectedGestures = mutableListOf<String>()

    private val poseLandmark by lazy {
        val baseOptionsBuilder = BaseOptions.builder().setModelAssetPath("pose_landmarker_lite.task")
        val baseOptions = baseOptionsBuilder.build()

        val optionsBuilder =
            PoseLandmarker.PoseLandmarkerOptions.builder()
                .setBaseOptions(baseOptions)
                .setResultListener { result, _ -> handleGestureRecognizerResult(result) }
                .setRunningMode(RunningMode.LIVE_STREAM)

        val options = optionsBuilder.build()

        PoseLandmarker.createFromOptions(getApplication(), options)
    }

    val uiState = MutableStateFlow(PoseTrainerUiState())

    val imageAnalyzer = ImageAnalysis.Analyzer { image ->
        val imageBitmap = image.toBitmap()
        val scale = 500f / max(image.width, image.height)

        val scaleAndRotate = Matrix().apply {
            postScale(scale, scale)
            postRotate(image.imageInfo.rotationDegrees.toFloat())
        }

        val scaledAndRotatedBmp = Bitmap.createBitmap(imageBitmap, 0, 0, image.width, image.height, scaleAndRotate, true)

        image.close()

        poseLandmark.detectAsync(
            BitmapImageBuilder(scaledAndRotatedBmp).build(),
            System.currentTimeMillis()
        )
    }

    private fun handleGestureRecognizerResult(result: PoseLandmarkerResult) {
        val firstLandmark = result.landmarks()
            .firstOrNull()

//        detectTPoseWithTimer(firstLandmark)
        detectGesture(firstLandmark)
    }

    private fun detectTPoseWithTimer(landmarks: MutableList<NormalizedLandmark>?): Boolean {
        if (isInTPose(landmarks)) {
            if (tPoseStartTime == 0L) {
                // Start timing when T-pose is first detected
                tPoseStartTime = System.currentTimeMillis()
            }

            // Check if holding for 1 second
            if (System.currentTimeMillis() - tPoseStartTime >= requiredHoldTime) {
                if (!isTPoseDetected) {
                    isTPoseDetected = true
                    uiState.value = PoseTrainerUiState("✅ T-Pose held for 1 second!")
                    listenForGesturesAsync(landmarks)
                }
                return true
            }
        }

        return false
    }

    private fun isInTPose(landmarks: MutableList<NormalizedLandmark>?): Boolean {
        if (landmarks != null) {
            val leftShoulder = landmarks[PoseLandmark.LEFT_SHOULDER]
            val rightShoulder = landmarks[PoseLandmark.RIGHT_SHOULDER]

            val leftElbow = landmarks[PoseLandmark.LEFT_ELBOW]
            val rightElbow = landmarks[PoseLandmark.RIGHT_ELBOW]

            val leftWrist = landmarks[PoseLandmark.LEFT_WRIST]
            val rightWrist = landmarks[PoseLandmark.RIGHT_WRIST]

            val verticalTolerance = 0.1f // Tolerance for y-alignment (5% of image height)
            val horizontalDistanceThreshold = 0.15f // Distance from shoulder for arms to be extende
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

            return leftAligned && rightAligned && armsExtended
        }

        return false;
    }

    private fun listenForGesturesAsync(landmarks: MutableList<NormalizedLandmark>?) {
        detectedGestures.clear()

        gestureJob?.cancel() // Cancel any previous running job
        gestureJob = CoroutineScope(Dispatchers.Default).launch {
            val startTime = System.currentTimeMillis()

            while (System.currentTimeMillis() - startTime < gestureTimeout) {
                val gesture = detectGesture(landmarks)

                if (gesture != null) {
                    detectedGestures.add(gesture)
                    uiState.value = PoseTrainerUiState("Gesture detected: $gesture")
                    delay(1000)
                    resetTPose()
                    return@launch
                }
            }

            // Timeout: Reset T-Pose detection
            resetTPose()
        }
    }


    private fun resetTPose() {
        tPoseStartTime = 0L
        isTPoseDetected = false
        detectedGestures.clear()
        gestureJob?.cancel()
        uiState.value = PoseTrainerUiState("")
    }

    private fun detectGesture(landmarks: MutableList<NormalizedLandmark>?): String? {
        if (landmarks == null) return null

        // Safe access to landmarks
        val leftShoulder = landmarks.getOrNull(PoseLandmark.LEFT_SHOULDER) ?: return null
        val rightShoulder = landmarks.getOrNull(PoseLandmark.RIGHT_SHOULDER) ?: return null
        val leftElbow = landmarks.getOrNull(PoseLandmark.LEFT_ELBOW) ?: return null
        val rightElbow = landmarks.getOrNull(PoseLandmark.RIGHT_ELBOW) ?: return null
        val leftWrist = landmarks.getOrNull(PoseLandmark.LEFT_WRIST) ?: return null
        val rightWrist = landmarks.getOrNull(PoseLandmark.RIGHT_WRIST) ?: return null

        // Threshold values (adjust if needed)
        val verticalTolerance = 0.1f  // Y-alignment tolerance
        val horizontalThreshold = 0.15f // Arm extension distance
        val upwardAngleThreshold = 0.15f // Angle for detecting "45° up"
        val elbowAngleThreshold = 90.0f // Elbow angle tolerance (degrees)

        // **Left Team Score +1** (Left Arm 90° Extended + Elbow 90° Up)
        val leftExtended = abs(leftElbow.x() - leftShoulder.x()) > horizontalThreshold  // Arm extended horizontally
        val leftElbowUp = leftElbow.y() < leftShoulder.y() && leftWrist.y() < leftElbow.y() // Elbow above shoulder, wrist above elbow

        // Calculate the angle at the left elbow (shoulder -> elbow -> wrist)
        val leftShoulderToElbow = floatArrayOf(leftElbow.x() - leftShoulder.x(), leftElbow.y() - leftShoulder.y())
        val leftElbowToWrist = floatArrayOf(leftWrist.x() - leftElbow.x(), leftWrist.y() - leftElbow.y())

        val leftElbowAngle = calculateAngle(leftShoulderToElbow, leftElbowToWrist)

        if (leftExtended && leftElbowUp && leftElbowAngle >= (90f - elbowAngleThreshold) && leftElbowAngle <= (90f + elbowAngleThreshold)) {
            return "Left Score +1"
        }

//        // **Left Team Score -1** (Left Arm 45° Up)
//        val leftArmUp = leftWrist.y() < leftShoulder.y() - upwardAngleThreshold &&
//                leftWrist.x() > leftShoulder.x()
//
//        if (leftArmUp) {
//            return "Left Score -1"
//        }
//
//        // **Right Team Score +1** (Right Arm 90° Extended + Elbow 90° Up)
//        val rightExtended = abs(rightElbow.x() - rightShoulder.x()) > horizontalThreshold  // Arm extended horizontally
//        val rightElbowUp = rightElbow.y() < rightShoulder.y() && rightWrist.y() < rightElbow.y() // Elbow above shoulder, wrist above elbow
//
//        if (rightExtended && rightElbowUp) {
//            return "Right Score +1"
//        }
//
//        // **Right Team Score -1** (Right Arm 45° Up)
//        val rightArmUp = rightWrist.y() < rightShoulder.y() - upwardAngleThreshold &&
//                rightWrist.x() < rightShoulder.x()
//
//        if (rightArmUp) {
//            return "Right Score -1"
//        }

        return null
    }

    // Function to calculate the angle between two vectors
    private fun calculateAngle(vector1: FloatArray, vector2: FloatArray): Float {
        val dotProduct = vector1[0] * vector2[0] + vector1[1] * vector2[1]
        val magnitude1 = Math.sqrt((vector1[0] * vector1[0] + vector1[1] * vector1[1]).toDouble()).toFloat()
        val magnitude2 = Math.sqrt((vector2[0] * vector2[0] + vector2[1] * vector2[1]).toDouble()).toFloat()

        val cosineTheta = dotProduct / (magnitude1 * magnitude2)
        val angleRad = Math.acos(cosineTheta.toDouble()).toFloat()
        return Math.toDegrees(angleRad.toDouble()).toFloat()
    }
}
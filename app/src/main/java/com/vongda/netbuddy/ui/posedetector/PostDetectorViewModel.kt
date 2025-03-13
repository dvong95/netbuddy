package com.vongda.netbuddy.ui.posetrainer

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.AndroidViewModel
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.max

data class PoseTrainerUiState(
    val mostRecentPose: MutableList<com.google.mediapipe.tasks.components.containers.NormalizedLandmark>? = null,
    val poseLandmarkerResult: PoseLandmarkerResult? = null
)

class PostTrainerViewModel(application: Application): AndroidViewModel(application) {
    private val poseLandmarker by lazy {
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

        poseLandmarker.detectAsync(
            BitmapImageBuilder(scaledAndRotatedBmp).build(),
            System.currentTimeMillis()
        )
    }

    private fun handleGestureRecognizerResult(result: PoseLandmarkerResult) {
        val firstLandmark = result.landmarks()
            .firstOrNull()
        uiState.value = PoseTrainerUiState(firstLandmark, result)
        Log.v("result", "Result: $firstLandmark")
    }
}
package com.vongda.netbuddy.ui.pointsignify

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import com.vongda.netbuddy.R
import com.vongda.netbuddy.data.mediapipe.ImageAnalyzer
import com.vongda.netbuddy.data.mediapipe.PoseManager
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.match.increaseScore
import com.vongda.netbuddy.ui.match.isGameOver
import java.util.concurrent.Executors

@Composable
fun PointScreen(
    vm: MatchViewModel = viewModel(),
    navigateToMatch: () -> Unit,
    navigateToResults: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val poseManager = remember { PoseManager(context, true) }

    var isPoseDetected by remember { mutableStateOf(false) }
    var holdTime by remember { mutableStateOf(0L) }
    var holdValid by remember { mutableStateOf(false) }
    val holdTarget: Long = 3000L
    val cooldownTime: Long = 5000
    var lastValid: Long = 0L

    // State for UI
    val poseData = remember { mutableStateOf<List<NormalizedLandmark>?>(null) }
    val currentPose = poseManager.getCurrentPoseName()
    val leftPose = "left_up"
    val rightPose = "right_up"

    //Set the listeners
    DisposableEffect(poseManager) {
        poseManager.setPoseListener { landmarks ->
            poseData.value = landmarks
        }

        //Watch for if still in pose and if detected
        poseManager.setOnPoseDetectedListener { detected ->
            isPoseDetected = detected
            if (!detected || (currentPose.value != leftPose || currentPose.value != rightPose)) {
                holdValid = false
            }
        }

        poseManager.setOnPoseHeldTimeListener { time ->
            holdTime = time
            val currTime = System.currentTimeMillis()

            if (time >= holdTarget && isPoseDetected && currentPose.value == leftPose &&  (currTime - lastValid >= cooldownTime)) {
                holdValid = true
                lastValid = currTime
                increaseScore(vm, "team1")

                if (isGameOver(vm)) {
                    Handler(Looper.getMainLooper()).post {
                        navigateToResults()
                    }
                }

                Handler(Looper.getMainLooper()).post {
                    navigateToMatch()
                }
            }

            if (time >= holdTarget && isPoseDetected && currentPose.value == rightPose && (currTime - lastValid >= cooldownTime)) {
                holdValid = true
                lastValid = currTime
                increaseScore(vm, "team2")


                if (isGameOver(vm)) {
                    Handler(Looper.getMainLooper()).post {
                        navigateToResults()
                    }
                }

                Handler(Looper.getMainLooper()).post {
                    navigateToMatch()
                }
            }
        }

        onDispose {
            poseManager.shutdown()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.mascot),
                contentDescription = "NetBuddy Mascot",
                modifier = Modifier.fillMaxWidth()
            )

            Text("Waiting for Point...", color = Color.White)
            Text("Waiting for Point...", color = Color.White)
        }

        //Invisible Camera View
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleX = 0.001f  // Make it essentially invisible
                    scaleY = 0.001f
                    alpha = 0.001f
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = androidx.camera.core.Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalyzer = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                        .also {
                            it.setAnalyzer(
                                Executors.newSingleThreadExecutor(),
                                ImageAnalyzer(poseManager)
                            )
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_FRONT_CAMERA,
                            preview,
                            imageAnalyzer
                        )
                    } catch (e: Exception) {
                        Log.e("PoseDetection", "Camera binding failed", e)
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier
                .size(1.dp)  // Minimal size
                .alpha(0f)   // Fully transparent
        )
    }
}
package com.vongda.handgestureprototype.ui.handgesture

import androidx.camera.core.ImageAnalysis
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vongda.netbuddy.ui.components.CameraPreview
import com.vongda.netbuddy.ui.posetrainer.PostTrainerViewModel

@Composable
fun PoseCameraScreen(
    modifier: Modifier = Modifier,
    viewModel: PostTrainerViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val imageAnalysisUseCase = remember {
        ImageAnalysis.Builder().build().apply {
            setAnalyzer(context.mainExecutor, viewModel.imageAnalyzer)
        }
    }

    Box(modifier = modifier) {
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            imageAnalysisUseCase = imageAnalysisUseCase
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            uiState?.let {
                Text(
                    text = "x : ${it.mostRecentPose?.firstOrNull()?.x()}, y : ${it.mostRecentPose?.firstOrNull()?.y()}, z : ${it.mostRecentPose?.firstOrNull()?.z()}",
                    color = Color.White,
                    fontSize = 50.sp
                )
            }
        }
    }
}
package com.vongda.netbuddy.data.mediapipe

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class ImageAnalyzer(private val poseManager: PoseManager) : ImageAnalysis.Analyzer {
    @androidx.camera.core.ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        poseManager.processImageProxy(imageProxy)
    }
}
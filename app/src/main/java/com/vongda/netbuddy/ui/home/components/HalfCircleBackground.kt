package com.vongda.netbuddy.ui.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

@Composable
fun HalfCircleBackground() {
    Canvas(
        modifier = Modifier.fillMaxSize().background(Color.Blue)
    ) {
        drawCircle(
            Color.Yellow,
            radius = size.width * 1.5f,
            Offset(size.width / 2, 0f)
        )
    }
}
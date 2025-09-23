package com.vongda.netbuddy.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainSurface(modifier: Modifier = Modifier, bodyContent: @Composable () -> Unit) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF5752AD)
    ) {
        bodyContent()
    }
}
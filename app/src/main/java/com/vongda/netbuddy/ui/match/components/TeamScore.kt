package com.vongda.netbuddy.ui.match.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TeamScore(
    maxWidth: Float,
    color: Color,
    name: String,
    score: Int,
    onClick: () -> Unit
) {
    Column (
        Modifier.fillMaxWidth(maxWidth).fillMaxHeight().background(color).clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(name, color = Color.White)
        Text("$score", color = Color.White, fontSize = 200.sp)
    }
}
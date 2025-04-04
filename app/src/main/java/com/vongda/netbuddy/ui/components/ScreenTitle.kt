package com.vongda.netbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle (title: String, fontSize: TextUnit, color: Color) {
    Column (modifier = Modifier.fillMaxWidth().padding(top = 26.dp, bottom = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text (
            text = title,
            fontSize = fontSize,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}
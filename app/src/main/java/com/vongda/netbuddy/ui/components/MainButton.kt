package com.vongda.netbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainButton (text: String, padding: Boolean = false, secondaryTheme: Boolean = false, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(28.dp)
            .shadow(
                elevation = 4.dp,
            ),
        border = if (secondaryTheme) BorderStroke(1.dp, Color.White) else null,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (secondaryTheme) Color(0xFF5752AD) else Color.White,
            contentColor = if (secondaryTheme) Color.White else Color.Black,

        ),
        shape = RoundedCornerShape(4.dp)
    ){
        Text(text = text,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(
                fontSize = 18.sp,
                color = if (secondaryTheme) Color.White else Color.Black,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.4f),
                    offset = Offset(0.5f, 0.5f),
                    blurRadius = 1f
                )
            )
        )
    }
}
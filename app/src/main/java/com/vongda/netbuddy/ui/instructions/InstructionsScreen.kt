package com.vongda.netbuddy.ui.instructions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.R
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.components.SmallLogoScreen
import com.vongda.netbuddy.ui.instructions.components.InstructionImage

@Composable
fun InstructionsScreen(modifier: Modifier = Modifier) {
    SmallLogoScreen {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            val context = LocalContext.current
            val instructions = readInstructions(context, R.raw.instructions)

            var currentIndex by remember { mutableStateOf(0) }
            val currentInstruction = instructions.getOrNull(currentIndex)

            ScreenTitle("INSTRUCTIONS", 32.sp, Color.White)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { if (currentIndex > 0) currentIndex-- }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "arrow back",
                        modifier = Modifier.size(40.dp),
                        tint = Color.LightGray
                    )
                }

                currentInstruction?.let {
                    InstructionImage(it.imageStr, it.imageDesc)
                }

                IconButton(
                    onClick = { if (currentIndex < instructions.lastIndex) currentIndex++ }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "arrow forward",
                        modifier = Modifier.size(40.dp),
                        tint = Color.LightGray
                    )
                }
            }

            currentInstruction?.let {
                ScreenTitle(it.title, 26.sp, Color.White)

                Text(
                    modifier = Modifier.width(300.dp).align(Alignment.CenterHorizontally),
                    text = it.body,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
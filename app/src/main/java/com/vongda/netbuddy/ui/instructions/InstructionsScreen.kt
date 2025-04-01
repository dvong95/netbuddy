package com.vongda.netbuddy.ui.instructions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.R
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.instructions.components.Instruction

@Composable
fun InstructionsScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier.fillMaxSize().background(Color.Yellow)
    ) {
        val context = LocalContext.current
        val instructions = readInstructions(context, R.raw.instructions)

        LazyColumn {
            item {
//                Column (Modifier.fillMaxWidth().background(Color.Blue)) {
                    ScreenTitle("• NetBuddy Instructions •", 28.sp, Color.Black)
//                }
            }


            items(
                items = instructions,
                key = { it.hashCode() }
            ) { instruction ->
                Instruction(instruction)
            }
        }
    }


}
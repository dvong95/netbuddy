package com.vongda.netbuddy.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vongda.netbuddy.ui.components.MainButton


@Composable
fun HomeScreenButtons(navigateToMatchSettings: () -> Unit, navigateToInstructions: () -> Unit) {
    Column (modifier = Modifier.fillMaxWidth().padding(36.dp)) {
        MainButton(
            text = "Start",
            padding = false,
            onClick = { navigateToMatchSettings() }
        )

        TextButton(
            onClick = { navigateToInstructions() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "How does NetBuddy work?",
                textAlign = TextAlign.Left,
                color = Color.LightGray
            )
        }
    }
}
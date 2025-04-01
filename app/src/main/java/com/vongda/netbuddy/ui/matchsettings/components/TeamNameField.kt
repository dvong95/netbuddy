package com.vongda.netbuddy.ui.matchsettings.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline

@Composable
fun TeamNameField(teamNum: String, teamNameValue: String, onValueChange: (String) -> Unit) {
    Text ("Team $teamNum", color = Color.Black)
    OutlinedTextField (
        value = teamNameValue,
        onValueChange = onValueChange,
        label = { Text("Name") },
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black
        )
    )
}
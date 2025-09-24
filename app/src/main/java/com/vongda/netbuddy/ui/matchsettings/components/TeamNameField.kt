package com.vongda.netbuddy.ui.matchsettings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TeamNameField(teamNum: String, teamNameValue: String, onValueChange: (String) -> Unit) {
    Text ("Team $teamNum", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth().padding(start=28.dp, end=28.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
            .padding(start = 28.dp, end = 28.dp),
            value = teamNameValue,
            onValueChange = onValueChange,
            label = { Text("Name") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedLabelColor = Color.White,
                unfocusedBorderColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            )
        )
}
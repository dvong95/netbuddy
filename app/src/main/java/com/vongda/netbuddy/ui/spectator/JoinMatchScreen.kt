package com.vongda.netbuddy.ui.spectator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.data.firebase.MatchHelper
import com.vongda.netbuddy.data.viewmodels.SpectatorViewModel
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.components.SmallLogoScreen

@Composable
fun JoinMatchScreen(
    svm: SpectatorViewModel,
    navigateToSpectatorMatch: () -> Unit
) {
    var matchCode by remember { mutableStateOf("") }
    var joinError by remember { mutableStateOf("") }
    SmallLogoScreen {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            ScreenTitle("Enter Match Code Below", 32.sp, Color.White)

            Text(joinError, fontSize = 20.sp)

            Spacer(Modifier.weight(0.5f))

            Text ("Match", color = Color.White, fontSize = 16.sp, modifier = Modifier.fillMaxWidth().padding(start=28.dp, end=28.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 28.dp, end = 28.dp),
                value = matchCode,
                onValueChange = { value ->
                    if (value.length <= 4) {
                        matchCode = value.uppercase()
                    }},
                label = { Text("Code") },
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

            MainButton(
                "Join Match",
                padding = true,
                onClick = {
                    if (matchCode.isNotEmpty()) {
                        MatchHelper.joinMatch(
                            matchCode,
                            onSuccess = {
                                joinError = ""
                                svm.matchCode = matchCode
                                navigateToSpectatorMatch()
                            },
                            onError = {
                                joinError = "Room $matchCode does not exist."
                            }
                        )
                    } else {
                        joinError = "Match Code required."
                    }

                }
            )
        }
    }
}
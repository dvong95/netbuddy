package com.vongda.netbuddy.ui.spectator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.SpectatorMatchEndScreen
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.components.SmallLogoScreen

@Composable
fun SpectatorMatchEndScreen(
    navigateToHome: () -> Unit
) {
    SmallLogoScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTitle("Host has ended the session.", 32.sp, Color.White)

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                MainButton(
                    text = "Return Home",
                    onClick = {
                        navigateToHome()
                    },
                )
            }
        }
    }
}
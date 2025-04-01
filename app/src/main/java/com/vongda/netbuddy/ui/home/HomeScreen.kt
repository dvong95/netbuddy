package com.vongda.netbuddy.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vongda.netbuddy.ui.home.components.HalfCircleBackground
import com.vongda.netbuddy.ui.home.components.HomeScreenButtons
import com.vongda.netbuddy.ui.home.components.HomeTitleLogo

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToMatchSettings: () -> Unit,
    navigateToInstructions: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        HalfCircleBackground()
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.weight(1f))
            HomeTitleLogo()
            Spacer(modifier = modifier.weight(1f))
            HomeScreenButtons(navigateToMatchSettings, navigateToInstructions)
        }
    }
}
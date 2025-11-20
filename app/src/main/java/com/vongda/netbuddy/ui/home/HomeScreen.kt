package com.vongda.netbuddy.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vongda.netbuddy.ui.components.LargeLogoScreen
import com.vongda.netbuddy.ui.components.MainButton

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToMatchSettings: () -> Unit,
    navigateToInstructions: () -> Unit,
    navigateToJoinMatch: () -> Unit
) {
    LargeLogoScreen (topContent = {
        Box (modifier = Modifier.fillMaxWidth().padding(28.dp)){
            IconButton(
                onClick = navigateToInstructions,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Instructions",
                    modifier = Modifier.size(40.dp).shadow(elevation = 2.dp, shape = CircleShape),
                    tint = Color.White
                )
            }
        }}) {
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy((-44).dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = modifier.weight(1f))
            MainButton(
                text = "Start New Match",
                onClick = { navigateToMatchSettings() }
            )
            MainButton(
                text = "Join Match",
                onClick = { navigateToJoinMatch() },
                secondaryTheme = true
            )
        }
    }
}
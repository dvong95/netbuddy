package com.vongda.netbuddy.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.R
import com.vongda.netbuddy.ui.components.ScreenTitle

@Composable
fun HomeTitleLogo() {
    Image(
        painter = painterResource(id = R.drawable.mainlogo),
        contentDescription = "NetBuddy mascot in front of volleyball net",
        modifier = Modifier.fillMaxWidth()
    )
    ScreenTitle("NetBuddy", 56.sp, Color.Black)
}
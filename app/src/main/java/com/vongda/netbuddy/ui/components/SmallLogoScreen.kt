package com.vongda.netbuddy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vongda.netbuddy.R

@Composable
fun SmallLogoScreen(modifier: Modifier = Modifier, topContent: @Composable () -> Unit = {}, bodyContent: @Composable () -> Unit) {
    MainSurface {
        Column {
            topContent()

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Small NetBuddyLogo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp)
                    .height(200.dp)
            )

            bodyContent()
        }
    }
}
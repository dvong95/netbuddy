package com.vongda.netbuddy.ui.instructions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vongda.netbuddy.R

@Composable
fun InstructionImage(imageStr: String, imageDesc: String) {
    val resourceId = when(imageStr) {
        "instructionsstart" -> R.drawable.instructionsstart
        "instructionsmatchsettings" -> R.drawable.instructionsmatchsettings
        "instructionscameraplacement" -> R.drawable.instructionscameraplacement
        "instructionsbeginmatch" -> R.drawable.instructionsbeginmatch
        "instructionsinitialpose" -> R.drawable.instructionsinitialpose
        "instructionsleftpose" -> R.drawable.instructionsleftpose
        "instructionsrightpose" -> R.drawable.instructionsrightpose
        else -> R.drawable.mascot
    }

    Image(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        painter = painterResource(id = resourceId),
        contentDescription = imageDesc,
        contentScale = ContentScale.Fit
    )
}
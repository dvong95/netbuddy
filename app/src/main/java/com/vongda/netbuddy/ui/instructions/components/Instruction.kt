package com.vongda.netbuddy.ui.instructions.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.data.models.Instruction

@Composable
fun Instruction(instruction: Instruction) {
    Column(
        Modifier.padding(8.dp).shadow(2.dp, shape = RoundedCornerShape(4.dp))
    ) {
        Column (Modifier.padding(16.dp)) {
            InstructionImage(instruction.imageStr, instruction.imageDesc)

            Text(
                instruction.title,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                instruction.body,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}
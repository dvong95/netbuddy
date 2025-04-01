package com.vongda.netbuddy.ui.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle

@Composable
fun ResultsScreen(
    vm: MatchViewModel,
    navigateToHome: () -> Unit,
    navigateToMatch: () -> Unit
) {
    Column (modifier = Modifier.fillMaxSize().background(Color.Yellow)) {
        ScreenTitle("Results", 36.sp, Color.Black)

        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text (
                "${vm.teamOneScore} - ${vm.teamTwoScore}",
                color = Color.Black,
                fontSize = 100.sp
            )
            Text (
                "${vm.matchWinner} Wins the Match!",
                color = Color.Black,
                fontSize = 28.sp
            )
        }

        Column (modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 100.dp, end = 100.dp)) {
            MainButton(
                text = "Play Again",
                padding = false,
                onClick = {
                    vm.teamOneScore = 0
                    vm.teamTwoScore = 0
                    vm.matchWinner = ""

                    navigateToMatch()
                }
            )
            TextButton(
                onClick = {
                    vm.teamOneName = ""
                    vm.teamTwoName = ""
                    vm.teamOneScore = 0
                    vm.teamTwoScore = 0
                    vm.overtimeEnabled = false
                    vm.matchWinner = ""

                    navigateToHome()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Create a New Match",
                    textAlign = TextAlign.Left,
                    color = Color.LightGray
                )
            }
        }

    }
}
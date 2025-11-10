package com.vongda.netbuddy.ui.results

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.vongda.netbuddy.data.firebase.MatchHelper
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.components.LargeLogoScreen
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.components.SmallLogoScreen

@Composable
fun ResultsScreen(
    vm: MatchViewModel,
    navigateToHome: () -> Unit,
    navigateToMatch: () -> Unit
) {
    SmallLogoScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTitle("Results", 32.sp, Color.White)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "${vm.teamOneScore} - ${vm.teamTwoScore}",
                    color = Color.White,
                    fontSize = 64.sp
                )
                Text(
                    "${vm.matchWinner} wins the Match!",
                    color = Color.White,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy((-44).dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MainButton(
                    text = "Play Again",
                    onClick = {
                        vm.teamOneScore = 0
                        vm.teamTwoScore = 0
                        vm.matchWinner = ""

                        MatchHelper.restartMatch(vm.matchCode)
                        navigateToMatch()
                    }
                )

                MainButton(
                    text = "End Match",
                    onClick = {
                        vm.teamOneName = ""
                        vm.teamTwoName = ""
                        vm.teamOneScore = 0
                        vm.teamTwoScore = 0
                        vm.overtimeEnabled = false
                        vm.matchWinner = ""

                        MatchHelper.endMatchSession(vm.matchCode)
                        vm.matchCode = ""
                        navigateToHome()
                    },
                    secondaryTheme = true
                )
            }
        }
    }
}
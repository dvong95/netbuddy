package com.vongda.netbuddy.ui.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vongda.handgestureprototype.ui.handgesture.PoseCameraScreen
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.match.components.TeamScore

@Composable
fun MatchScreen(
    vm: MatchViewModel = viewModel(),
    navigateToResults: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        //PoseCameraScreen()
        Column (modifier = Modifier.fillMaxSize().background(Color.Yellow)
        ) {
            Row {
                TeamScore(
                    0.5f,
                    Color.Blue,
                    vm.teamOneName,
                    vm.teamOneScore,
                    onClick = {
                        increaseScore(vm, "team1")

                        if (isGameOver(vm)) {
                            navigateToResults()
                        }
                    }
                )

                TeamScore(
                    1f,
                    Color.Red,
                    vm.teamTwoName,
                    vm.teamTwoScore,
                    onClick = {
                        increaseScore(vm, "team2")

                        if (isGameOver(vm)) {
                            navigateToResults()
                        }
                    }
                )
            }
        }


        //TODO: Add this back once we've enabled the camera listener and if T pose detected display this and wait for next pose
//        Box (
//            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
//        ) {
//            Column (
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.mascot),
//                    contentDescription = "NetBuddy Mascot",
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Text("Waiting for Point...", color = Color.White)
//            }
//        }

    }

}
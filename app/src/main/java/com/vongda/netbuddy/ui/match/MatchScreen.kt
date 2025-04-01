package com.vongda.netbuddy.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.R
import com.vongda.netbuddy.data.viewmodels.MatchViewModel

@Composable
fun MatchScreen(
    vm: MatchViewModel,
    navigateToResults: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxSize().background(Color.Yellow)
        ) {
            Row {
                Column (
                    Modifier.fillMaxWidth(0.5f).fillMaxHeight().background(Color.Blue).clickable {
                        //TODO: If max score reached (check for overtime too) then go to ResultsScreen.kt and pass Match object
                        if (vm.teamOneScore < 25) {
                            vm.teamOneScore++
                        }

                        //Add check for overtime
                        if (vm.teamOneScore == 25) {
                            navigateToResults()
                        }
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(vm.teamOneName, color = Color.White)
                    Text("${vm.teamOneScore}", color = Color.White, fontSize = 200.sp)
                }
                Column(
                    Modifier.fillMaxWidth(1f).fillMaxHeight().background(Color.Red).clickable {
                        //TODO: If max score reached (check for overtime too) then go to results and pass Match object

                        //increase score 2
                        if (vm.teamTwoScore < 25) {
                            vm.teamTwoScore++
                        }

                        if (vm.teamTwoScore == 25) {
                            navigateToResults()
                        }
                    },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(vm.teamTwoName, color = Color.White)
                    Text("${vm.teamTwoScore}", color = Color.White, fontSize = 200.sp)
                }
            }
        }


        //TODO: Add this back once we've enabled the camera listener and if T pose detected display this and wait for next pose
        Box (
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(
                    painter = painterResource(id = R.drawable.mascot),
                    contentDescription = "NetBuddy Mascot",
                    modifier = Modifier.fillMaxWidth()
                )

                Text("Waiting for Point...", color = Color.White)
            }
        }
    }

}
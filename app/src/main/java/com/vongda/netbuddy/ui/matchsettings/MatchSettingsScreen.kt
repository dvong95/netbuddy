package com.vongda.netbuddy.ui.matchsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.matchsettings.components.TeamNameField

@Composable
fun MatchSettingsScreen(
    vm: MatchViewModel,
    navigateToMatch: () -> Unit
) {


    Column (
        modifier = Modifier.fillMaxSize().background(Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.5f))

        ScreenTitle("Match Settings", 44.sp, Color.Black)

        Spacer(Modifier.weight(0.5f))

        Column (
            verticalArrangement = Arrangement.Center,
        ) {
            Column {
                TeamNameField (teamNum = "One", teamNameValue = vm.teamOneName, onValueChange = { value -> vm.teamOneName = value})
            }

            Column (modifier = Modifier.padding(top = 16.dp)) {
                TeamNameField (teamNum = "Two", teamNameValue = vm.teamTwoName, onValueChange = { value -> vm.teamTwoName = value})
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Text("Overtime", color = Color.Black)
            Switch(
                checked = vm.overtimeEnabled,
                onCheckedChange = { vm.overtimeEnabled = it },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = Color.DarkGray,
                    uncheckedBorderColor = Color.DarkGray,
                    uncheckedTrackColor = Color.LightGray,
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Green
                )
            )
        }

        Spacer(Modifier.weight(1f))

        MainButton(
            "Begin Match",
            padding = true,
            onClick = {
                //Add validation
                vm.matchWinner = ""
                vm.teamOneScore = 0
                vm.teamTwoScore = 0
                navigateToMatch()
            }
        )

        Spacer(Modifier.weight(0.5f))

    }
}
package com.vongda.netbuddy.ui.spectator

import android.service.autofill.FieldClassification.Match
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ListenerRegistration
import com.vongda.netbuddy.data.firebase.MatchHelper
import com.vongda.netbuddy.data.viewmodels.SpectatorViewModel
import com.vongda.netbuddy.ui.components.MainButton
import com.vongda.netbuddy.ui.components.ScreenTitle
import com.vongda.netbuddy.ui.components.SmallLogoScreen

@Composable
fun SpectatorResultsScreen(
    svm: SpectatorViewModel,
    navigateToHome: () -> Unit,
    navigateToSpectatorMatch: () -> Unit,
    navigateToSpectatorMatchEnd: () -> Unit
) {
    var listenerRegistration: ListenerRegistration? = null

    var winnerOfMatch by remember { mutableStateOf("") }
    var team1Score by remember { mutableStateOf(0) }
    var team2Score by remember { mutableStateOf(0) }

    listenerRegistration = MatchHelper.listenForUpdates(svm.matchCode) { teamA, teamB, winner, finalScoreReached, deleted  ->
        if (deleted) {
            navigateToSpectatorMatchEnd()
            listenerRegistration?.remove()
            listenerRegistration = null
        } else {
            val teamAScore = (teamA?.get("score") as Long).toInt()
            val teamBScore = (teamB?.get("score") as Long).toInt()

            team1Score = teamAScore
            team2Score = teamBScore
            winnerOfMatch = winner

            if (!finalScoreReached && winner == "") {
                navigateToSpectatorMatch()
                listenerRegistration?.remove()
                listenerRegistration = null
            }
        }
    }

    SmallLogoScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenTitle("Results", 32.sp, Color.White)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$team1Score - $team2Score",
                    color = Color.White,
                    fontSize = 64.sp
                )
                Text(
                    "$winnerOfMatch wins the Match!",
                    color = Color.White,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Waiting on Host...",
                    color = Color.White,
                    fontSize = 20.sp
                )
                MainButton(
                    text = "Exit Match Session",
                    onClick = {
                        listenerRegistration?.remove()
                        listenerRegistration = null
                        navigateToHome()
                    },
                )
            }
        }
    }
}
package com.vongda.netbuddy.ui.spectator

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.ListenerRegistration
import com.vongda.netbuddy.data.firebase.MatchHelper
import com.vongda.netbuddy.data.viewmodels.SpectatorViewModel

@Composable
fun SpectatorMatchScreen(
    svm: SpectatorViewModel,
    navigateToSpectatorResults: () -> Unit
) {
    var listenerRegistration: ListenerRegistration? = null

    var team1Name by remember { mutableStateOf("") }
    var team2Name by remember { mutableStateOf("") }
    var team1Score by remember { mutableStateOf(0) }
    var team2Score by remember { mutableStateOf(0) }

    listenerRegistration = MatchHelper.listenForUpdates(svm.matchCode) { teamA, teamB, winner, finalScoreReached, deleted  ->
        val teamAName = teamA?.get("name") as String
        val teamAScore = (teamA["score"] as Long).toInt()
        val teamBName = teamB?.get("name") as String
        val teamBScore = (teamB["score"] as Long).toInt()

        team1Name = teamAName
        team1Score = teamAScore

        team2Name = teamBName
        team2Score = teamBScore

        if (winner.isNotEmpty() && finalScoreReached) {
            navigateToSpectatorResults()
            listenerRegistration?.remove()
            listenerRegistration = null
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Yellow)
        ) {
            Row {
                Column(
                    Modifier.fillMaxWidth(0.5f).fillMaxHeight().background(Color.Blue),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(team1Name, color = Color.White)
                    Text("$team1Score", color = Color.White, fontSize = 200.sp)
                }

                Column(
                    Modifier.fillMaxWidth(1f).fillMaxHeight().background(Color.Red),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(team2Name, color = Color.White)
                    Text("$team2Score", color = Color.White, fontSize = 200.sp)
                }
            }
        }
    }
}
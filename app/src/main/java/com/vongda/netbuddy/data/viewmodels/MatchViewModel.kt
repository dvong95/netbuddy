package com.vongda.netbuddy.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class MatchViewModel {
    var teamOneName by mutableStateOf("")
    var teamTwoName by mutableStateOf("")

    var teamOneScore by mutableStateOf(0)
    var teamTwoScore by mutableStateOf(0)

    var overtimeEnabled by mutableStateOf(false)

    var matchWinner by mutableStateOf("")
}
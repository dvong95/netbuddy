package com.vongda.netbuddy.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MatchViewModel : ViewModel() {
    var teamOneName by mutableStateOf("")
    var teamTwoName by mutableStateOf("")

    var teamOneScore by mutableIntStateOf(0)
    var teamTwoScore by mutableIntStateOf(0)

    var overtimeEnabled by mutableStateOf(false)

    var matchWinner by mutableStateOf("")
}
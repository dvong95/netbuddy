package com.vongda.netbuddy.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SpectatorViewModel : ViewModel() {
    var matchCode by mutableStateOf("")
}
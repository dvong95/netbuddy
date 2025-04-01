package com.vongda.netbuddy.ui.match

import com.vongda.netbuddy.data.viewmodels.MatchViewModel

fun increaseScore(vm: MatchViewModel, team: String) {
    if (!vm.overtimeEnabled) {
        if (team == "team1") {
            if (vm.teamOneScore < 25) {
                vm.teamOneScore++
            }
        } else if (team == "team2") {
            if (vm.teamTwoScore < 25) {
                vm.teamTwoScore++
            }
        }
    } else {
        if (team == "team1") {
            if (!isWinningOvertime(vm.teamOneScore, vm.teamTwoScore)) {
                vm.teamOneScore++
            }
        } else if (team == "team2") {
            if (!isWinningOvertime(vm.teamTwoScore, vm.teamOneScore)) {
                vm.teamTwoScore++
            }
        }
    }
}

fun isWinningOvertime(winningScore: Int, losingScore: Int) : Boolean {
    if (winningScore == 25 && losingScore <= 23) {
        return true
    }
    if (winningScore >= 25 && winningScore == losingScore + 2) {
        return true
    }

    return false
}

fun isGameOver(vm: MatchViewModel) : Boolean {
    var gameOverFlag: Boolean = false

    if (!vm.overtimeEnabled) {
        if (vm.teamOneScore == 25) {
            vm.matchWinner = vm.teamOneName
            gameOverFlag = true
        } else if (vm.teamTwoScore == 25) {
            vm.matchWinner = vm.teamTwoName
            gameOverFlag = true
        }
    } else {
        if (isWinningOvertime(vm.teamOneScore, vm.teamTwoScore)) {
            vm.matchWinner = vm.teamOneName
            gameOverFlag = true
        } else if (isWinningOvertime(vm.teamTwoScore, vm.teamOneScore)) {
            vm.matchWinner = vm.teamTwoName
            gameOverFlag = true
        }
    }

    return gameOverFlag
}

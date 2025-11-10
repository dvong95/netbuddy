package com.vongda.netbuddy.ui.match

import com.vongda.netbuddy.data.firebase.MatchHelper
import com.vongda.netbuddy.data.viewmodels.MatchViewModel

fun increaseScore(vm: MatchViewModel, team: String) {
    if (!vm.overtimeEnabled) {
        if (team == "team1") {
            if (vm.teamOneScore < 25) {
                MatchHelper.updateScore(vm.matchCode, "team1", ++vm.teamOneScore)
            }
        } else if (team == "team2") {
            if (vm.teamTwoScore < 25) {
                MatchHelper.updateScore(vm.matchCode, "team2", ++vm.teamTwoScore)
            }
        }
    } else {
        if (team == "team1") {
            if (!isWinningOvertime(vm.teamOneScore, vm.teamTwoScore)) {
                MatchHelper.updateScore(vm.matchCode, "team1", ++vm.teamOneScore)
            }
        } else if (team == "team2") {
            if (!isWinningOvertime(vm.teamTwoScore, vm.teamOneScore)) {
                MatchHelper.updateScore(vm.matchCode, "team2", ++vm.teamTwoScore)
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
            MatchHelper.endMatch(vm.matchCode, vm.teamOneName)
            vm.matchWinner = vm.teamOneName
            gameOverFlag = true
        } else if (vm.teamTwoScore == 25) {
            MatchHelper.endMatch(vm.matchCode, vm.teamTwoName)
            vm.matchWinner = vm.teamTwoName
            gameOverFlag = true
        }
    } else {
        if (isWinningOvertime(vm.teamOneScore, vm.teamTwoScore)) {
            MatchHelper.endMatch(vm.matchCode, vm.teamOneName)
            vm.matchWinner = vm.teamOneName
            gameOverFlag = true
        } else if (isWinningOvertime(vm.teamTwoScore, vm.teamOneScore)) {
            MatchHelper.endMatch(vm.matchCode, vm.teamTwoName)
            vm.matchWinner = vm.teamTwoName
            gameOverFlag = true
        }
    }

    return gameOverFlag
}

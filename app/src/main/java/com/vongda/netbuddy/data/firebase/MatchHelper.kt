package com.vongda.netbuddy.data.firebase

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore

object MatchHelper {
    private val auth = Firebase.auth

    fun createMatch(
        matchCode: String,
        team1Name: String,
        team2Name: String,
        overtimeEnabled: Boolean
    ) {
        val db = Firebase.firestore

        val match = hashMapOf(
            "hostId" to auth.currentUser?.uid,
            "team1" to hashMapOf(
                "name" to team1Name,
                "score" to 0
            ),
            "team2" to hashMapOf(
                "name" to team2Name,
                "score" to 0
            ),
            "overtime" to overtimeEnabled,
            "createdAt" to FieldValue.serverTimestamp(),
            "winner" to "",
            "finalScoreReached" to false
        )

        db.collection("matches").document(matchCode)
            .set(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Match created: $matchCode")
            }
    }

    fun updateScore(
        matchCode: String,
        team: String,
        score: Int
    ) {
        val fieldPath = "$team.score"

        val db = Firebase.firestore

        db.collection("matches").document(matchCode)
            .update(fieldPath, score)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Updated $team score: $score")
            }
    }

    fun endMatch(matchCode: String, winner: String) {
        val fieldPath = "winner"
        val fieldPath2 = "finalScoreReached"
        val db = Firebase.firestore

        db.collection("matches").document(matchCode)
            .update( mapOf(
                fieldPath to winner,
                fieldPath2 to true
            ) )
            .addOnSuccessListener {
                Log.d("FIREBASE", "Updated winner and final score")
            }
    }

    fun restartMatch(matchCode: String) {
        val db = Firebase.firestore

        val match = mapOf(
            "team1.score" to 0,
            "team2.score" to 0,
            "winner" to "",
            "finalScoreReached" to false
        )

        db.collection("matches").document(matchCode)
            .update(match)
            .addOnSuccessListener {
                Log.d("FIREBASE", "Reset values of match")
            }
    }

    fun joinMatch(
        matchCode: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val db = Firebase.firestore

        db.collection("matches").document(matchCode)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) onSuccess() else onError()
            }
            .addOnFailureListener { onError() }
    }

    fun listenForUpdates(
        roomCode: String,
        onUpdate: (team1: Map<String, Any>, team2: Map<String, Any>, winner: String, finalScoreReached: Boolean) -> Unit
    ): ListenerRegistration {
        val db = Firebase.firestore

        return db.collection("matches").document(roomCode)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && snapshot.exists()) {
                    val team1 = snapshot.get("team1") as Map<String, Any>
                    val team2 = snapshot.get("team2") as Map<String, Any>
                    val winner = snapshot.get("winner") as String
                    val finalScoreReached = snapshot.get("finalScoreReached") as Boolean
                    onUpdate(team1, team2, winner, finalScoreReached)
                }
            }
    }

    fun endMatchSession(matchCode: String) {
        val db = Firebase.firestore

        db.collection("matches").document(matchCode)
            .delete()
            .addOnSuccessListener { Log.d("FIREBASE", "Deleted Match $matchCode") }
            .addOnFailureListener { e -> Log.e("FIREBASE", "Delete failed", e)}
    }
}
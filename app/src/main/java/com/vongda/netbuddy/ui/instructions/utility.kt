package com.vongda.netbuddy.ui.instructions

import android.content.Context
import android.util.Log
import com.vongda.netbuddy.data.models.Instruction
import kotlinx.serialization.json.Json

fun readInstructions(context: Context, resId: Int) : List<Instruction> {
    var instructions = emptyList<Instruction>()

    try {
        val text = context.resources.openRawResource(resId).bufferedReader().use { it.readText() }

        instructions = Json.decodeFromString<List<Instruction>>(text)

    } catch (e: Error) {
        Log.e("Error: ", e.toString())
    }

    return instructions
}
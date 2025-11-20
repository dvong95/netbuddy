package com.vongda.netbuddy.ui.matchsettings

fun generateMatchCode() : String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..4).map { chars.random() }.joinToString("")
}
package com.vongda.netbuddy.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Instruction(
    @SerialName("Title") val title: String,
    @SerialName("Body") val body: String,
    @SerialName("ImageStr") val imageStr: String,
    @SerialName("ImageDesc") val imageDesc: String
)

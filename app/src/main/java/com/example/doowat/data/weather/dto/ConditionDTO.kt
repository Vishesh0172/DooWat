package com.example.doowat.data.weather.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConditionDTO(
    @SerialName("text")
    val text: String,

    val code: Int
)
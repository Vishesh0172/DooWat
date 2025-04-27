package com.example.doowat.data.weather.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrentTemperatureDTO(
    val temp_c: Float,
    val temp_f: Float,
    val is_day: Int,
    val condition: ConditionDTO
)
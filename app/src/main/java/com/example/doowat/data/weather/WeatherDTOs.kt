package com.example.doowat.data.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDTO(

    @SerialName("current")
    val current: CurrentTemperatureDTO,

    @SerialName("location")
    val location: LocationDTO
)

@Serializable
data class LocationDTO(
    val country: String,
    val name: String,
    val region: String
)

@Serializable
data class CurrentTemperatureDTO(
    val temp_c: Float,
    val temp_f: Float,
    val is_day: Int,
    val condition: ConditionDTO
)

@Serializable
data class ConditionDTO(
    @SerialName("text")
    val text: String,

    val code: Int
)

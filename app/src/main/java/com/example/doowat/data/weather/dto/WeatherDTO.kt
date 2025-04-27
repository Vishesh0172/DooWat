package com.example.doowat.data.weather.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDTO(

    @SerialName("current")
    val current:CurrentTemperatureDTO,

    @SerialName("location")
    val location:LocationDTO
)

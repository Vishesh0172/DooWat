package com.example.doowat.data.weather.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    val country: String,
    val name: String,
    val region: String
)
package com.example.doowat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoute {

    @Serializable
    data class PlacesArgs(
        val isDay: Boolean,
        val latitude: Double,
        val longitude: Double,
        val conditionCode: Int,
    ): NavRoute

}

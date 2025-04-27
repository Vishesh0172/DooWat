package com.example.doowat.presentation.home

import com.example.doowat.domain.model.Condition
import com.example.doowat.presentation.common.LoadingState

data class HomeState(
    val loadingState: LoadingState = LoadingState.Loading("Fetching Weather"),
    val tempF: Float = 0f,
    val tempC: Float = 0f,
    val ambientTemperature: Float = 0f,
    val locationPermission: Boolean = false,
    val region: String = "",
    val city: String = "",
    val condition: String = "",
    val country: String = "",
    val isDay: Boolean = false,
    val conditionCode: Int = 0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val conditionType: Condition = Condition.ClearDark,
    val activityText: String = ""
)
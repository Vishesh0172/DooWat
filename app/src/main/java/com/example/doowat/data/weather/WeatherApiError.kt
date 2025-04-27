package com.example.doowat.data.weather

sealed class WeatherApiError(val errorMessage: String) {

    data class NetworkError(val message: String): WeatherApiError(message)
    data class FetchError(val message: String): WeatherApiError(message)
}
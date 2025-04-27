package com.example.doowat.data.places

sealed class PlacesApiError(val errorMessage: String) {

    data class EmptyResult(val message: String): PlacesApiError(message)
    data class NetworkError(val message: String): PlacesApiError(message)
}
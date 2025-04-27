package com.example.doowat.presentation.common

interface LoadingState {
    data object Success: LoadingState
    data class Error(val message: String): LoadingState
    data class Loading(val message: String): LoadingState
}
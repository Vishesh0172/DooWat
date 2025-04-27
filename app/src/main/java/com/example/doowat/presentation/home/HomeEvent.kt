package com.example.doowat.presentation.home

import android.location.Location

sealed interface HomeEvent {

    data class LocationFetched(val location: Location?): HomeEvent
}
package com.example.doowat.presentation.places

import com.example.doowat.presentation.common.LoadingState
import com.example.doowat.data.places.PlaceDTO

data class PlacesState(
    val placesList: List<PlaceDTO> = emptyList(),
    val loadingState: LoadingState = LoadingState.Loading("Loading Places that suit the weather")
)
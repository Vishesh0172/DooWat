package com.example.doowat.presentation.places

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import arrow.core.getOrElse
import com.example.doowat.data.places.PlacesApiError
import com.example.doowat.data.places.PlacesRepository
import com.example.doowat.domain.usecase.GetConditionUseCase
import com.example.doowat.domain.usecase.GetQueryByConditionUseCase
import com.example.doowat.presentation.common.LoadingState
import com.example.doowat.presentation.navigation.NavRoute.PlacesArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PlacesViewModel(
    private val placesRepository: PlacesRepository,
    getConditionUseCase: GetConditionUseCase,
    getQueryByConditionUseCase: GetQueryByConditionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow(PlacesState())
    val state = _state.asStateFlow()
    val arguments = savedStateHandle.toRoute<PlacesArgs>()

    init {

        val condition = getConditionUseCase(arguments.conditionCode, arguments.isDay)
        val query = getQueryByConditionUseCase(condition)

        viewModelScope.launch(Dispatchers.IO) {

            val placesResult = placesRepository.getPlaces(query, arguments.latitude, arguments.longitude) {
                launch(Dispatchers.IO) {
                    it.imgUrl.value = placesRepository.getPlacePhoto(it.id)
                }
            }

            val places = placesResult.getOrElse { error: PlacesApiError ->
                Log.d("PlacesVM", error.errorMessage)
                _state.update { it.copy(loadingState = LoadingState.Error(error.errorMessage)) }
                null
            }

            places?.let {
                _state.update { it.copy(placesList = places, loadingState = LoadingState.Success) }
            }
        }

    }

}





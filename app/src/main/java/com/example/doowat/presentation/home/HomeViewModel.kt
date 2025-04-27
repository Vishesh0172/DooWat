package com.example.doowat.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import com.example.doowat.data.weather.WeatherApiError
import com.example.doowat.data.weather.WeatherRepository
import com.example.doowat.domain.usecase.GetConditionUseCase
import com.example.doowat.domain.usecase.GetQueryByConditionUseCase
import com.example.doowat.presentation.common.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "HomeViewModel"
class HomeViewModel(
    private val weatherRepository: WeatherRepository,
    val getConditionUseCase: GetConditionUseCase,
    val getQueryByConditionUseCase: GetQueryByConditionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private lateinit var coordinates: String




    fun onEvent(event: HomeEvent) {

        when(event) {
            is HomeEvent.LocationFetched -> {
                event.location?.let { location ->
                    _state.update { it.copy(locationPermission = true) }

                    coordinates = "${location.latitude},${location.longitude}"
                    _state.update { it.copy(latitude = location.latitude, longitude = location.longitude) }

                    viewModelScope.launch(Dispatchers.IO){
                        getWeatherAndUpdate()
                    }
                }
            }
        }
    }


    private suspend fun CoroutineScope.getWeatherAndUpdate(){

        val result = async { weatherRepository.getWeatherDetails(coordinates) }
        val weather = result.await().getOrElse { error: WeatherApiError ->
            Log.d(TAG, error.errorMessage)
            _state.update { it.copy(loadingState = LoadingState.Error("Couldn't Fetch Weather Details")) }
            null
        }



        weather?.let { weatherDetails ->

            val isDay = weatherDetails.current.is_day != 0
            val conditionType = getConditionUseCase(
                conditionCode = weatherDetails.current.condition.code,
                isDay = isDay
            )

            val query = getQueryByConditionUseCase(conditionType)
            val activityText = "Perfect conditions for $query"

            _state.update { it.copy(
                loadingState = LoadingState.Success,
                tempC = weatherDetails.current.temp_c,
                tempF = weatherDetails.current.temp_f,
                region = weatherDetails.location.region,
                city = weatherDetails.location.name,
                condition = weatherDetails.current.condition.text,
                country = weatherDetails.location.country,
                isDay = isDay,
                conditionCode = weatherDetails.current.condition.code,
                conditionType = conditionType,
                activityText = activityText
            ) }
        }

    }

}
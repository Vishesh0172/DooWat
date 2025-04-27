package com.example.doowat.data.weather

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import com.example.doowat.data.weather.dto.WeatherDTO


interface WeatherRepository{


    suspend fun getWeatherDetails(coordinates: String): Either<WeatherApiError, WeatherDTO>

}

class WeatherRepositoryImpl(
    private val api: WeatherApi
): WeatherRepository {


    override suspend fun getWeatherDetails(coordinates: String): Either<WeatherApiError, WeatherDTO> {

        val response =  api.getWeatherDetails(coordinates = coordinates)
        val body = response.body()


        return either {
            ensure(response.isSuccessful){ WeatherApiError.NetworkError("Response not Successful")}
            ensureNotNull(body){ WeatherApiError.FetchError("Information not available") }
            body
        }

    }


}
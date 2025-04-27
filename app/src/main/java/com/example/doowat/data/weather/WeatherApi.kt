package com.example.doowat.data.weather

import com.example.doowat.BuildConfig
import com.example.doowat.data.weather.dto.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("current.json")
    suspend fun getWeatherDetails(
        @Query("key") key: String = BuildConfig.WEATHER_API_KEY,
        @Query("q") coordinates: String
    ): Response<WeatherDTO>

}
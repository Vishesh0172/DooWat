package com.example.doowat.di

import com.example.doowat.BuildConfig
import com.example.doowat.data.places.PlacesRepository
import com.example.doowat.data.places.PlacesRepositoryImpl
import com.example.doowat.data.weather.WeatherApi
import com.example.doowat.data.weather.WeatherRepository
import com.example.doowat.data.weather.WeatherRepositoryImpl
import com.example.doowat.domain.usecase.GetConditionUseCase
import com.example.doowat.domain.usecase.GetQueryByConditionUseCase
import com.google.android.libraries.places.api.Places
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module{

    single{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.WEATHER_API_URL)
            .build()
            .create(WeatherApi::class.java)
    }

    single<WeatherRepository>{
        WeatherRepositoryImpl(get())
    }

    single{
        Places.createClient(get())
    }

    single<PlacesRepository>{
        PlacesRepositoryImpl(get())
    }





}
package com.example.doowat

import android.app.Application
import com.example.doowat.di.dataModule
import com.example.doowat.di.useCaseModule
import com.example.doowat.di.viewModelModule
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DooWatApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DooWatApplication)
            modules(dataModule, viewModelModule, useCaseModule)
        }

        Places.initializeWithNewPlacesApiEnabled(this, BuildConfig.PLACES_API_KEY)
    }
}
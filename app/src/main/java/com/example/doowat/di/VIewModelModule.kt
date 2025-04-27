package com.example.doowat.di

import com.example.doowat.AppViewModel
import com.example.doowat.presentation.home.HomeViewModel
import com.example.doowat.presentation.places.PlacesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel{
        HomeViewModel(get(), get(), get())
    }

    viewModel{
        PlacesViewModel(get(), get(), get(), get())
    }

    viewModel{
        AppViewModel()
    }


}
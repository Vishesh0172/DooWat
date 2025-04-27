package com.example.doowat.di

import com.example.doowat.domain.usecase.GetConditionUseCase
import com.example.doowat.domain.usecase.GetQueryByConditionUseCase
import org.koin.dsl.module

val useCaseModule = module{
    single{
        GetConditionUseCase()
    }

    single{
        GetQueryByConditionUseCase()
    }
}
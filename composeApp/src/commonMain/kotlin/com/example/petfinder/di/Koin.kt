package com.example.petfinder.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import com.example.data_core.di.networkModule
import com.example.data.di.dataModule

expect val platformModule: Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            networkModule,
            dataModule,
            platformModule
        )
    }

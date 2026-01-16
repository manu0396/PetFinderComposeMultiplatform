package com.example.petfinder.di

import com.example.data.di.dataModule
import com.example.data_core.di.networkModule
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.petfinder.BuildKonfig
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.logger.Level
import org.koin.core.qualifier.named

expect val platformModule: Module

val appModule = module {
    single(named("UNSPLASH_KEY")) { BuildKonfig.UNSPLASH_KEY }
}

fun initKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {}
) =
    startKoin {
        printLogger(Level.DEBUG)
        appDeclaration()
        modules(
            appModule,
            networkModule,
            dataModule,
            platformModule,
            commonModules
        )
        modules(additionalModules)
    }

val commonModules = module {
    single { GetAnimalImagesUseCase(get()) }
    viewModel {
        AnimalViewModel(
            get(),
             get(),
            get()
        )
    }
}

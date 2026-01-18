package com.example.petfinder.di

import com.example.data.di.dataModule
import com.example.data.di.useCaseModule
import com.example.data.repository.AnimalRepositoryImpl
import com.example.data_core.di.networkModule
import com.example.domain.repository.AnimalRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.petfinder.BuildKonfig
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    single<AnimalRepository> {
        AnimalRepositoryImpl(get(), get(), get())
    }

    factory { GetAnimalImagesUseCase(get()) }

    viewModelOf(::AnimalViewModel)

    single(named("UNSPLASH_KEY")) { BuildKonfig.UNSPLASH_KEY }
}

fun initKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    printLogger(Level.DEBUG)
    appDeclaration()
    modules(
        appModule,     // Módulo principal de la App
        networkModule, // Módulo de red (:data-core)
        dataModule,    // Módulo de base de datos (:data)
        platformModule,
        useCaseModule// Módulo específico de plataforma
    )
    modules(additionalModules)
}

object KoinHelper {
    fun doInit() {
        initKoin {
            // Configuración específica de iOS si fuera necesaria
        }
    }
}

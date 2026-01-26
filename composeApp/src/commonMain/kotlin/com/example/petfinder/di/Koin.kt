package com.example.petfinder.di

import com.example.data.di.dataModule
import com.example.data.di.useCaseModule
import com.example.data.repository.AnimalRepositoryImpl
import com.example.data.repository.FavoriteRepositoryImpl
import com.example.data_core.di.networkModule
import com.example.domain.repository.AnimalRepository
import com.example.domain.repository.FavoriteRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import com.example.domain.useCase.GetFavoritesUseCase
import com.example.domain.useCase.ToggleFavoriteUseCase
import com.example.petfinder.BuildKonfig
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.example.feature_login.di.loginModule
import com.example.session.di.sessionModule

expect val platformModule: Module

val domainModule = module {
    factory { ToggleFavoriteUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { GetAnimalImagesUseCase(get()) }
}
val viewModelModule = module {
    viewModelOf(::AnimalViewModel)
}
val appModule = module {
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
    single<AnimalRepository> { AnimalRepositoryImpl(get(), get(), get()) }
    single(named("UNSPLASH_KEY")) { BuildKonfig.UNSPLASH_KEY }
}

fun initKoin(
    additionalModules: List<Module> = emptyList(),
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    printLogger(Level.DEBUG)
    appDeclaration()
    modules(
        appModule,
        networkModule,
        dataModule,
        domainModule,
        viewModelModule,
        platformModule,
        loginModule,
        sessionModule,
        useCaseModule,
        *additionalModules.toTypedArray()
    )
}

object KoinHelper {
    fun doInit() {
        initKoin {
            // Logs automáticos para iOS
        }
    }
}

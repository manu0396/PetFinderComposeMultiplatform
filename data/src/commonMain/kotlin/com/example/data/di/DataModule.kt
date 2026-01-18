package com.example.data.di

import com.bbva.petfinder.data.db.AnimalDb
import com.example.data.repository.AnimalRepositoryImpl
import com.example.data.repository.FavoriteRepositoryImpl
import com.example.domain.repository.AnimalRepository
import com.example.domain.repository.FavoriteRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

expect val dataPlatformModule: Module

val dataModule = module {
    single<AnimalRepository> { AnimalRepositoryImpl(get(), get(), get(), scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
    single<AnimalDb> { AnimalDb(driver = get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(db = get(), logger = get()) }
    includes(dataPlatformModule)
}
val useCaseModule = module {
    factory { GetAnimalImagesUseCase(get()) }
}

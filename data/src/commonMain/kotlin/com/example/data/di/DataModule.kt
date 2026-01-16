package com.example.data.di

import com.example.data.db.AnimalDb
import com.example.data.repository.AnimalRepositoryImpl
import com.example.data.repository.FavoriteRepositoryImpl
import com.example.domain.repository.AnimalRepository
import com.example.domain.repository.FavoriteRepository
import com.example.domain.useCase.GetAnimalImagesUseCase
import org.koin.dsl.module

val dataModule = module {
    single<AnimalRepository> { AnimalRepositoryImpl(get(), get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
    single<AnimalDb> { AnimalDb(driver = get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(db = get(), logger = get()) }
}
val useCaseModule = module {
    factory { GetAnimalImagesUseCase(get()) }
}

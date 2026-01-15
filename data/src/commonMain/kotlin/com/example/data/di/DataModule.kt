package com.example.data.di

import com.example.data.remote.UnsplashRemoteDataSource
import com.example.data.repository.AnimalRepositoryImpl
import com.example.domain.repository.AnimalRepository
import org.koin.dsl.module

val dataModule = module {
    single { UnsplashRemoteDataSource(get()) }

    single<AnimalRepository> { AnimalRepositoryImpl(get()) }
}

package com.example.data.di

import com.example.data.repository.AnimalRepositoryImpl
import com.example.domain.repository.AnimalRepository
import org.koin.dsl.module

val dataModule = module {
    single<AnimalRepository> { AnimalRepositoryImpl(get()) }
}

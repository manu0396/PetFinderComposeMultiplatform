package com.example.data.di

import com.example.data.repository.ImageRepository
import org.koin.dsl.module

val dataModule = module {
    single { ImageRepository(get()) }
}

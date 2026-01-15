package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.remote.UnsplashRemoteDataSource
import org.koin.dsl.module

val androidDataModule = module {
    single {
        UnsplashRemoteDataSource(
            client = get(),
            apiKey = BuildConfig.UNSPLASH_KEY
        )
    }
}

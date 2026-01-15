package com.example.petfinder.di

import com.example.data.BuildConfig
import com.example.data.remote.UnsplashRemoteDataSource
import org.koin.dsl.module

actual val platformModule = module {
    single {
        UnsplashRemoteDataSource(
            client = get(),
            apiKey = BuildConfig.UNSPLASH_KEY
        )
    }
}

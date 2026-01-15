package com.example.petfinder.di

import com.example.data.remote.UnsplashRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.petfinder.BuildKonfig

actual val platformModule: Module = module {
    single {
        UnsplashRemoteDataSource(
            client = get(),
            apiKey = BuildKonfig.UNSPLASH_KEY
        )
    }
}

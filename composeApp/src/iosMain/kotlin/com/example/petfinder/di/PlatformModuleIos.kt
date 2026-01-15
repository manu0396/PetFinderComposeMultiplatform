package com.example.petfinder.di

import com.example.data.remote.UnsplashRemoteDataSource
import com.example.domain.util.AppLogger
import org.koin.core.module.Module
import org.koin.dsl.module
import com.example.petfinder.BuildKonfig
import platform.Foundation.NSLog

class IosLogger : AppLogger {
    override fun d(tag: String, message: String) { NSLog("[$tag] DEBUG: $message") }
    override fun e(tag: String, message: String, throwable: Throwable?) {
        NSLog("[$tag] ERROR: $message. Error: ${throwable?.message}")
    }
}

actual val platformModule: Module = module {
    single<AppLogger> { IosLogger() }
    single {
        UnsplashRemoteDataSource(
            client = get(),
            apiKey = BuildKonfig.UNSPLASH_KEY
        )
    }
}

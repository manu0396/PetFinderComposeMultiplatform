package com.example.petfinder.di

import android.util.Log
import com.example.data.di.androidDataModule
import com.example.data.remote.UnsplashRemoteDataSource
import com.example.domain.util.AppLogger
import org.koin.dsl.module

class AndroidLogger : AppLogger {
    override fun d(tag: String, message: String) { Log.d(tag, message) }
    override fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}


actual val platformModule = module {
    includes(androidDataModule)
    single<AppLogger> { AndroidLogger() }
    single {
        UnsplashRemoteDataSource(
            client = get()
        )
    }
}

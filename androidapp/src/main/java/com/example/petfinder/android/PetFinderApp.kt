package com.example.petfinder.android

import android.app.Application
import com.example.petfinder.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class PetFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@PetFinderApp)
        }
    }
}

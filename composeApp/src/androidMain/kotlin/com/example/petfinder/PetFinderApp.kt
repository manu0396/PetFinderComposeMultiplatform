package com.example.petfinder

import android.app.Application
import com.example.data.di.androidDataModule
import com.example.petfinder.di.initKoin
import org.koin.android.ext.koin.androidContext

class PetFinderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            additionalModules = listOf(androidDataModule)
        ){
            androidContext(this@PetFinderApp)
        }
    }
}

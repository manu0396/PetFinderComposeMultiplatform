package com.example.petfinder.android

import android.app.Application
import com.example.petfinder.di.initKoin
import org.koin.android.ext.koin.androidContext

class PetFinderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            appDeclaration = {
                androidContext(this@PetFinderApp)
            }
        )
    }
}

package com.example.petfinder.app

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.example.data.di.androidDataModule
import com.example.petfinder.di.initKoin
import io.ktor.client.HttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class PetFinderApp : Application(), SingletonImageLoader.Factory {
    private val httpClient: HttpClient by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PetFinderApp)
            modules(androidDataModule)
        }

    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(KtorNetworkFetcherFactory(httpClient))
            }
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }
}

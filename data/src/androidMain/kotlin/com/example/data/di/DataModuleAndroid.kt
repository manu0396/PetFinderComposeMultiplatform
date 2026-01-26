package com.example.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.remote.UnsplashRemoteDataSource
import com.example.data.db.driver.DriverFactory
import com.example.petfinder.data.db.AnimalDb
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

val androidDataModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = AnimalDb.Schema,
            context = get(),
            name = "animal.db"
        )
    }
    single {
        UnsplashRemoteDataSource(
            client = get()
        )
    }
}

actual val dataPlatformModule: Module = module {
    includes(androidDataModule)
    single {
        DriverFactory(androidContext()).createDriver()
    }
}

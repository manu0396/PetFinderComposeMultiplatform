package com.example.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.db.AnimalDb
import com.example.data.remote.UnsplashRemoteDataSource
import org.koin.dsl.module

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

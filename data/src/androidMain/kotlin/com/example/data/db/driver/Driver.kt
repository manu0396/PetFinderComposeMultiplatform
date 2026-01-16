package com.example.data.db.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.db.AnimalDb
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object AndroidDriverFactory : KoinComponent {
    fun create(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AnimalDb.Schema,
            context = get<Context>(),
            name = "animal.db"
        )
    }
}

actual fun createDriver(): SqlDriver = AndroidDriverFactory.create()

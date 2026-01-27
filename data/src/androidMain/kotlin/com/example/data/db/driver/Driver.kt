package com.example.data.db.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.db.AnimalDb

class DriverFactory(private val context: Context) {
    fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = AnimalDb.Schema,
            context = context,
            name = "animal.db"
        )
    }
}

package com.example.data.db.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.data.db.AnimalDb

class DriverFactory {
    fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = AnimalDb.Schema,
            name = "animal.db"
        )
    }
}

package com.example.data.db.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.data.db.AnimalDb

actual fun createDriver(): SqlDriver =
    NativeSqliteDriver(AnimalDb.Schema, "animal.db")

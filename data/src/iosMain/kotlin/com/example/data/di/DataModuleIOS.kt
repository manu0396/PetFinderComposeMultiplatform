package com.example.data.di

import com.example.data.db.driver.DriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataPlatformModule: Module = module {
    single {
        DriverFactory().createDriver()
    }
}

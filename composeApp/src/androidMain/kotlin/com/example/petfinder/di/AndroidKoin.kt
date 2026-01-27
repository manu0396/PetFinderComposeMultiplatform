package com.example.petfinder.di

import com.example.data.di.androidDataModule
import com.example.domain.util.AppLogger
import com.example.petfinder.logger.AndroidAppLogger
import org.koin.dsl.module

actual val platformModule = module {
    includes(androidDataModule)
    single<AppLogger> { AndroidAppLogger() }
}

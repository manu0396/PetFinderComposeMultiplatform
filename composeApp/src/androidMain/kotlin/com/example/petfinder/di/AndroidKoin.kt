package com.example.petfinder.di

import com.example.data.di.androidDataModule
import org.koin.dsl.module

actual val platformModule = module {
    includes(androidDataModule)
}

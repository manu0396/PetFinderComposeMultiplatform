package com.example.environment.di

import com.example.environment.config.BuildKonfigEnvironment
import com.example.environment.config.EnvironmentConfig
import org.koin.dsl.module

val environmentModule = module {
    single<EnvironmentConfig> { BuildKonfigEnvironment() }
}

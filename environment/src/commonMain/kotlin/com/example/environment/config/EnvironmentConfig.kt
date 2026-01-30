package com.example.environment.config

import com.example.environment.model.AppEnvironment
import com.example.environment.model.AppFlavor

interface EnvironmentConfig {
    val flavor: AppFlavor
    val environment: AppEnvironment
    val baseUrl: String
    val apiKey: String
    fun isDebug(): Boolean
}

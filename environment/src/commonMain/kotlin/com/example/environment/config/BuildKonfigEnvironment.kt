package com.example.environment.config

import com.example.environment.BuildKonfig
import com.example.environment.model.AppEnvironment
import com.example.environment.model.AppFlavor

class BuildKonfigEnvironment : EnvironmentConfig {
    override val flavor: AppFlavor
        get() = AppFlavor.fromString(BuildKonfig.FLAVOR)

    override val environment: AppEnvironment
        get() = AppEnvironment.fromString(BuildKonfig.ENV)

    override val baseUrl: String
        get() = BuildKonfig.BASE_URL

    override val apiKey: String
        get() = BuildKonfig.API_KEY

    override fun isDebug(): Boolean {
        return environment != AppEnvironment.PROD
    }
}

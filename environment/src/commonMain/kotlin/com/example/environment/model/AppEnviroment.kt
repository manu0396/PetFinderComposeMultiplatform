package com.example.environment.model

enum class AppEnvironment {
    DEV,
    QA,
    PROD;

    companion object {
        fun fromString(value: String): AppEnvironment {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: DEV
        }
    }
}

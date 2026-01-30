package com.example.environment.model

enum class AppFlavor {
    SPAIN,
    PERU,
    MEXICO,
    GLOMO,
    UNKNOWN;

    companion object {
        fun fromString(value: String): AppFlavor {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}

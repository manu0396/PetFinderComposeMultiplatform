package com.example.data.mapper

import io.ktor.client.plugins.*
import kotlinx.serialization.SerializationException
import com.example.domain.model.DomainError

fun Throwable.toDomainError(): DomainError {
    return when (this) {
        is ClientRequestException -> {
            if (response.status.value == 401) DomainError.Unauthorized
            else DomainError.Unknown("Client Error: ${response.status}")
        }
        is ServerResponseException -> DomainError.Server
        is SerializationException -> DomainError.Serialization
        else -> DomainError.Network
    }
}

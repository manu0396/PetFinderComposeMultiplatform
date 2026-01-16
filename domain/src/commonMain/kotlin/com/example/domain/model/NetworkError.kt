package com.example.domain.model

sealed class DomainError : Throwable() {
    object Network : DomainError()
    object Unauthorized : DomainError()
    object Server : DomainError()
    object Serialization : DomainError()
    data class Unknown(val msg: String) : DomainError()
}

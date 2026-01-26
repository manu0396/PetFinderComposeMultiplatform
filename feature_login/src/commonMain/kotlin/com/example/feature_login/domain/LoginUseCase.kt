package com.example.feature_login.domain

import com.example.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    private fun validate(email: String, pass: String): Result<Unit> {
        return when {
            email.isBlank() -> Result.failure(Exception("Email cannot be empty"))
            pass.length < 6 -> Result.failure(Exception("Password must be at least 6 characters"))
            else -> Result.success(Unit)
        }
    }

    suspend fun signIn(email: String, pass: String): Result<Unit> {
        validate(email, pass).onFailure { return Result.failure(it) }
        return repository.signIn(email, pass)
    }

    suspend fun signUp(email: String, pass: String): Result<Unit> {
        validate(email, pass).onFailure { return Result.failure(it) }
        return repository.signUp(email, pass)
    }
}

package com.example.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val userSession: Flow<String?>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signOut()
    suspend fun signUp(email: String, password: String): Result<Unit>
}

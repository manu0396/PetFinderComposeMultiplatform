package com.example.session

import com.example.domain.repository.AuthRepository
import com.example.session.domain.SessionState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionManager(
    private val authRepository: AuthRepository,
) {
    val currentState: Flow<SessionState> = authRepository.userSession
        .map { uid ->
            if (uid != null) SessionState.Authenticated(uid)
            else SessionState.NotAuthenticated
        }
    suspend fun signOut() {
        authRepository.signOut()
    }
}

package com.example.session.domain

sealed class SessionState {
    data object Loading : SessionState()
    data class Authenticated(val userId: String) : SessionState()
    data object NotAuthenticated : SessionState()
}

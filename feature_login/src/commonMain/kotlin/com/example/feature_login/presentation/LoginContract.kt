package com.example.feature_login.presentation

class LoginContract {
    data class State(
        val email: String = "",
        val password: String = "",
        val mode: AuthMode = AuthMode.LOGIN,
        val isLoading: Boolean = false,
        val error: String? = null,
        val isSuccess: Boolean = false
    )

    // 2. What the user can DO (Intents)
    sealed interface Event {
        data class EmailChanged(val email: String) : Event
        data class PasswordChanged(val password: String) : Event
        data object ToggleMode : Event
        data object LoginClicked : Event
        data object ConsumeSuccess : Event
    }

    // 3. One-time side effects (Snackbars, Navigations)
    sealed interface Effect {
        data class ShowError(val message: String) : Effect
    }

    enum class AuthMode { LOGIN, REGISTER }
}

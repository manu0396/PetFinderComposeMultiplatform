package com.example.feature_login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_login.domain.LoginUseCase
import com.example.feature_login.presentation.LoginContract.AuthMode
import com.example.feature_login.presentation.LoginContract.Event
import com.example.feature_login.presentation.LoginContract.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    fun onEvent(event: Event) {
        when (event) {
            is Event.EmailChanged -> {
                _uiState.update { it.copy(email = event.email, error = null) }
            }
            is Event.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password, error = null) }
            }
            is Event.ToggleMode -> {
                _uiState.update {
                    it.copy(
                        mode = if (it.mode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN,
                        error = null
                    )
                }
            }
            is Event.LoginClicked -> performAuthentication()
            is Event.ConsumeSuccess -> {
                _uiState.update { it.copy(isSuccess = false) }
            }
        }
    }

    private fun performAuthentication() {
        val currentState = _uiState.value
        if (currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = if (currentState.mode == AuthMode.LOGIN) {
                loginUseCase.signIn(currentState.email, currentState.password)
            } else {
                loginUseCase.signUp(currentState.email, currentState.password)
            }

            result
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Authentication Error"
                        )
                    }
                }
        }
    }
}

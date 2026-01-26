package com.example.feature_login.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.components.resources.Res
import com.example.components.resources.btn_login
import com.example.components.resources.btn_register
import com.example.components.resources.label_email
import com.example.components.resources.label_password
import com.example.components.resources.login_welcome
import com.example.components.resources.register_title
import com.example.components.resources.toggle_to_login
import com.example.components.resources.toggle_to_register
import com.example.feature_login.presentation.LoginContract
import com.example.feature_login.presentation.LoginViewModel
import com.example.feature_login.ui.components.ErrorMessage
import com.example.shared.components.AppButton
import com.example.shared.components.AppTextField
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinInject()
) {
    val state by viewModel.uiState.collectAsState()

    LoginContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LoginContent(
    state: LoginContract.State,
    onEvent: (LoginContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                if (state.mode == LoginContract.AuthMode.LOGIN) Res.string.login_welcome else Res.string.register_title
            ),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        AppTextField(
            value = state.email,
            onValueChange = { onEvent(LoginContract.Event.EmailChanged(it)) },
            label = stringResource(Res.string.label_email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = state.password,
            onValueChange = { onEvent(LoginContract.Event.PasswordChanged(it)) },
            label = stringResource(Res.string.label_password),
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.error != null) {
            ErrorMessage(message = state.error!!)
        }

        Spacer(modifier = Modifier.height(24.dp))

        AppButton(
            text = stringResource(
                if (state.mode == LoginContract.AuthMode.LOGIN) Res.string.btn_login else Res.string.btn_register
            ),
            isLoading = state.isLoading,
            onClick = { onEvent(LoginContract.Event.LoginClicked) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { onEvent(LoginContract.Event.ToggleMode) }
        ) {
            Text(
                text = stringResource(
                    if (state.mode == LoginContract.AuthMode.LOGIN) Res.string.toggle_to_register else Res.string.toggle_to_login
                )
            )
        }
    }
}

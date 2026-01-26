package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.session.SessionManager
import org.koin.compose.koinInject
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val sessionManager: SessionManager = koinInject()
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Ajustes de Cuenta",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        isLoading = true
                        sessionManager.signOut()
                    } catch (e: Exception) {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onError,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Cerrar Sesión")
            }
        }
    }
}

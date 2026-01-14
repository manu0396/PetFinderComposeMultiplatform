package com.example.petfinder

// 1. Imports de UI Básica (Estos sí suelen mantenerse como androidx porque son compartidos)
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import org.jetbrains.compose.resources.painterResource

// 3. Recursos generados (Asegúrate de que el paquete sea el correcto)
import petfinder.composeapp.generated.resources.Res
import petfinder.composeapp.generated.resources.compose_multiplatform

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeContent), // Mejor práctica para KMP
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Si 'Res' sigue en rojo, mira el paso de abajo
                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = null
                    )
                    Text("Compose: Hello PetFinder!")
                }
            }
        }
    }
}

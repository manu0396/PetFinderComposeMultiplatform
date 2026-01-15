package com.example.petfinder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import org.jetbrains.compose.resources.painterResource
import com.example.petfinder.components.Res
import com.example.petfinder.components.compose_multiplatform

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeContent),
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

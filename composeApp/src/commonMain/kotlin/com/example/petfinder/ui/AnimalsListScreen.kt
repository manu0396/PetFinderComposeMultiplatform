package com.example.petfinder.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.petfinder.ui.components.AnimalItem
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.compose.viewmodel.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AnimalListScreen(
    viewModel: AnimalViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAnimals()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        items(state) { animal ->
            AnimalItem(animal = animal)
        }
    }
}

package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.petfinder.ui.components.AnimalItem
import com.example.petfinder.viewmodel.AnimalUiState
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AnimalsListScreen(
    state: AnimalUiState,
    viewModel: AnimalViewModel = koinViewModel()
) {
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    when (state) {
        is AnimalUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AnimalUiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = state.animals, key = { it.id }) { animal ->
                    val isFav = favorites.any { it.id == animal.id }
                    AnimalItem(
                        animal = animal,
                        isFavorite = isFav,
                        onFavoriteClick = { viewModel.toogleFavorite(animal) }
                    )
                }
            }
        }
        is AnimalUiState.Error -> {
            Text(state.message, color = MaterialTheme.colorScheme.error)
        }
    }
}

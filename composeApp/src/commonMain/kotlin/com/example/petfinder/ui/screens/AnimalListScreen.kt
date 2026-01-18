package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.model.Animal
import com.example.petfinder.ui.components.AnimalItem
import com.example.petfinder.viewmodel.AnimalUiState

@Composable
fun AnimalsListScreen(
    state: AnimalUiState,
    favorites: List<Animal>,
    onToggleFavorite: (Animal) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is AnimalUiState.Idle -> {
                Text(
                    "Busca una mascota para comenzar.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            is AnimalUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is AnimalUiState.Success -> {
                if (state.animals.isEmpty()) {
                    Text(
                        "No hay mascotas que mostrar.",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.animals,
                            key = { it.id }
                        ) { animal ->
                            val isFav = remember(favorites) {
                                favorites.any { it.id == animal.id }
                            }

                            AnimalItem(
                                animal = animal,
                                isFavorite = isFav,
                                onFavoriteClick = { onToggleFavorite(animal) }
                            )
                        }
                    }
                }
            }
            is AnimalUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }
        }
    }
}

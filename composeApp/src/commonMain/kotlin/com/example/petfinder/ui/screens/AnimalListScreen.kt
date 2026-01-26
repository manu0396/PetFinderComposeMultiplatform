package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.components.resources.Res
import com.example.components.resources.msg_no_results
import com.example.domain.model.Animal
import com.example.petfinder.ui.components.AnimalItem
import com.example.petfinder.viewmodel.AnimalUiState
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnimalsListScreen(
    state: AnimalUiState,
    favorites: List<Animal>,
    filters: List<String>,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    onAddFilterClick: () -> Unit,
    onToggleFavorite: (Animal) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filters) { filter ->
                val isSelected = filter.equals(selectedFilter, ignoreCase = true)
                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectFilter(filter) },
                    label = { Text(filter.replaceFirstChar { it.uppercase() }) },
                    leadingIcon = if (isSelected) {
                        { Icon(Icons.Default.Check, contentDescription = null) }
                    } else null
                )
            }

            item {
                ElevatedAssistChip(
                    onClick = onAddFilterClick,
                    label = { Text("New") },
                    leadingIcon = {
                        Icon(Icons.Default.Add, contentDescription = "Add category")
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            if (state.animals.isNotEmpty()) {
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
            } else if (!state.isLoading && state.error == null) {
                Text(
                    stringResource(Res.string.msg_no_results),
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            state.error?.let { errorText ->
                Text(
                    text = errorText.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

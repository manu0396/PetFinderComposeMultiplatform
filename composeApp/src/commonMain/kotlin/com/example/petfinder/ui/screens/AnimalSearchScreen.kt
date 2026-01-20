package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.petfinder.ui.components.AddPetFilterDialog
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AnimalSearchScreen(
    viewModel: AnimalViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val focusManager = LocalFocusManager.current

    // 1. Manejo del Diálogo (Levantado por el ViewModel)
    if (state.isAddFilterDialogOpen) {
        AddPetFilterDialog(
            onDismiss = { viewModel.onDismissAddFilterDialog() },
            onConfirm = { newType ->
                viewModel.onConfirmAddFilter(newType)
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Find your pet",
            style = MaterialTheme.typography.headlineMedium
        )

        // 2. Barra de Búsqueda Persistente
        // Value: Viene del VM (se mantiene al rotar o navegar)
        // OnChange: Actualiza el VM inmediatamente
        OutlinedTextField(
            value = state.currentQuery,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search (e.g. cute, small)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.performSearch()
                focusManager.clearFocus()
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Lista de Resultados con Filtros
        AnimalsListScreen(
            state = state,
            favorites = favorites,
            filters = state.filters,             // Lista dinámica
            selectedFilter = state.selectedFilter,
            onSelectFilter = { viewModel.onFilterSelected(it) },
            onAddFilterClick = { viewModel.onAddFilterClicked() },
            onToggleFavorite = { animal -> viewModel.toggleFavorite(animal) },
            modifier = Modifier.weight(1f)
        )
    }
}

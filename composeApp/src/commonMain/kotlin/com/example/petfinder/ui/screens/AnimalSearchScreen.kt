package com.example.petfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.petfinder.ui.components.AddPetFilterDialog
import com.example.petfinder.viewmodel.AnimalIntent
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AnimalSearchScreen(
    viewModel: AnimalViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    if (state.isAddFilterDialogOpen) {
        AddPetFilterDialog(
            existingFilters = state.filters,
            onDismissRequest = { viewModel.handleIntent(AnimalIntent.CloseFilterDialog) },
            onApplyFilters = { filter -> viewModel.handleIntent(AnimalIntent.ApplyPetFilter(filter)) }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Find your pet", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = state.currentQuery,
            onValueChange = { viewModel.handleIntent(AnimalIntent.QueryChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search (e.g. cute, small)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.handleIntent(AnimalIntent.RefreshSearch)
                focusManager.clearFocus()
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        // FIXED: Solo pasamos 'state' y 'favorites'. Quitamos parámetros redundantes.
        AnimalsListScreen(
            state = state,
            favorites = favorites,
            onSelectFilter = { viewModel.handleIntent(AnimalIntent.FilterSelected(it)) },
            onAddFilterClick = { viewModel.handleIntent(AnimalIntent.OpenFilterDialog) },
            onToggleFavorite = { animal -> viewModel.handleIntent(AnimalIntent.ToggleFavorite(animal)) },
            modifier = Modifier.weight(1f)
        )
    }
}

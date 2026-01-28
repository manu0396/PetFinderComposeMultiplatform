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

    if (state.isAddFilterDialogOpen) {
        AddPetFilterDialog(
            existingFilters = state.filters,
            onDismissRequest = {
                viewModel.onDismissAddFilterDialog()
            },
            onApplyFilters = { filter ->
                viewModel.onApplyFilters(filter)
            }
        )
    }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Find your pet",
            style = MaterialTheme.typography.headlineMedium
        )
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
        AnimalsListScreen(
            state = state,
            favorites = favorites,
            filters = state.filters,
            selectedFilter = state.selectedFilter,
            onSelectFilter = { viewModel.onFilterSelected(it) },
            onAddFilterClick = { viewModel.onAddFilterClicked() },
            onToggleFavorite = { animal -> viewModel.toggleFavorite(animal) },
            modifier = Modifier.weight(1f)
        )
    }
}

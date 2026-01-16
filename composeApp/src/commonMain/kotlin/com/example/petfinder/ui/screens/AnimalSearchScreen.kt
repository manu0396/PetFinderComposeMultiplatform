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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.petfinder.viewmodel.AnimalViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AnimalSearchScreen(
    viewModel: AnimalViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    var searchQuery by remember { mutableStateOf(viewModel.lastQuery) }
    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Find your new friend", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search (dog, cat...)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.searchAnimals(searchQuery)
                focusManager.clearFocus()
            })
        )

        Spacer(modifier = Modifier.height(16.dp))

        AnimalsListScreen(
            state = state,
            favorites = favorites,
            onToggleFavorite = { animal -> viewModel.toggleFavorite(animal) },
            modifier = Modifier.weight(1f) // Para que ocupe el espacio restante
        )
    }
}

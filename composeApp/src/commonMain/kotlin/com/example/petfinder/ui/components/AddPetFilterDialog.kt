package com.example.petfinder.ui.components

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.components.resources.Res
import com.example.components.resources.dialog_add_search_description
import com.example.components.resources.dialog_add_search_label
import com.example.components.resources.dialog_add_search_placeholder
import com.example.components.resources.dialog_add_search_suggestions
import com.example.components.resources.dialog_add_search_title
import com.example.components.resources.action_add_search
import com.example.components.resources.action_cancel
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddPetFilterDialog(
    existingFilters: List<String>,
    onDismissRequest: () -> Unit,
    onApplyFilters: (PetFilter) -> Unit
) {
    var customType by remember { mutableStateOf("") }
    val allSuggestions = remember { listOf("Hamster", "Rabbit", "Bird", "Horse", "Fish") }

    val visibleSuggestions = remember(existingFilters) {
        allSuggestions.filterNot { suggestion ->
            existingFilters.any { it.equals(suggestion, ignoreCase = true) }
        }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(Res.string.dialog_add_search_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = stringResource(Res.string.dialog_add_search_description),
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = customType,
                    onValueChange = { customType = it },
                    label = { Text(stringResource(Res.string.dialog_add_search_label)) },
                    placeholder = { Text(stringResource(Res.string.dialog_add_search_placeholder)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                if (visibleSuggestions.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = stringResource(Res.string.dialog_add_search_suggestions),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            visibleSuggestions.forEach { suggestion ->
                                FilterChip(
                                    selected = customType.equals(suggestion, ignoreCase = true),
                                    onClick = { customType = suggestion },
                                    label = { Text(suggestion) }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = customType.isNotBlank(),
                onClick = {
                    onApplyFilters(PetFilter(type = customType.trim()))
                    onDismissRequest()
                }
            ) {
                Text(stringResource(Res.string.action_add_search))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(Res.string.action_cancel))
            }
        }
    )
}

data class PetFilter(val type: String)

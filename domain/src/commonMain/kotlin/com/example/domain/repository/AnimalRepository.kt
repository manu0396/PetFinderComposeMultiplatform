package com.example.domain.repository

import com.example.domain.model.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AnimalRepository {
    val favorites: StateFlow<List<Animal>>
    val searchResults: SharedFlow<List<Animal>>
    suspend fun searchAnimals(query: String)
    suspend fun toggleFavorite(animal: Animal)
    fun isFavorite(id: String): Flow<Boolean>
}

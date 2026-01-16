package com.example.domain.repository

import com.example.domain.model.Animal
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Animal>>
    suspend fun toggleFavorite(animal: Animal)
    suspend fun isFavorite(id: String): Boolean
}

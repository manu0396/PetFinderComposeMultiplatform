package com.example.domain.repository

import com.example.domain.model.Animal
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Animal>>
    suspend fun addFavorite(animal: Animal)
    suspend fun removeFavorite(id: String)
    suspend fun isFavorite(id: String): Boolean
}

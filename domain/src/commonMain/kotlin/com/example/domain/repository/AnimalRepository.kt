package com.example.domain.repository

import com.example.domain.model.Animal
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    suspend fun searchAnimals(query: String): List<Animal>
    fun toogleFavorite(animal: Animal)
    fun isFavorite(id: String): Flow<Boolean>
    fun getFavorites(): Flow<List<Animal>>
}

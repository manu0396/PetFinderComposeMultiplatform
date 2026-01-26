package com.example.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.data.mapper.toDomain
import com.example.domain.model.Animal
import com.example.domain.repository.FavoriteRepository
import com.example.domain.util.AppLogger
import com.example.petfinder.data.db.AnimalDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class FavoriteRepositoryImpl(
    private val db: AnimalDb,
    private val logger: AppLogger
) : FavoriteRepository {
    private val queries = db.animalDbQueries

    override fun getFavorites(): Flow<List<Animal>> =
        queries.selectAllFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    @OptIn(ExperimentalTime::class)
    override suspend fun addFavorite(animal: Animal) {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        queries.insertFavorite(
            id = animal.id,
            name = animal.name,
            description = animal.description,
            imageUrl = animal.imageUrl,
            createdAt = timestamp
        )
    }

    override suspend fun removeFavorite(id: String) {
        queries.deleteFavorite(id)
    }

    override suspend fun isFavorite(id: String): Boolean =
        queries.selectFavoriteById(id).executeAsOneOrNull() != null
}

package com.example.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bbva.petfinder.data.db.AnimalDb
import com.example.data.mapper.toDomain
import com.example.domain.model.Animal
import com.example.domain.repository.FavoriteRepository
import com.example.domain.util.AppLogger
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

    override fun getFavorites(): Flow<List<Animal>> {
        return queries.selectAllFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                entities.map { it.toDomain() }
            }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun toggleFavorite(animal: Animal) {
        val isFav = isFavorite(animal.id)
        if (isFav) {
            queries.deleteFavorite(animal.id)
        } else {
            val timestamp = Clock.System.now().toEpochMilliseconds()

            queries.insertFavorite(
                id = animal.id,
                name = animal.name,
                description = animal.description,
                imageUrl = animal.imageUrl,
                createdAt = timestamp
            )
        }
    }

    override suspend fun isFavorite(id: String): Boolean {
        return queries.selectFavoriteById(id).executeAsOneOrNull() != null
    }
}

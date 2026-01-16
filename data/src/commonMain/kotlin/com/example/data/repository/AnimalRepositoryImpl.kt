package com.example.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.data.db.AnimalDb
import com.example.data.remote.UnsplashRemoteDataSource
import com.example.data.remote.models.UnsplashPhotoDto
import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository
import com.example.domain.util.AppLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimalRepositoryImpl(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val db: AnimalDb,
    private val logger: AppLogger,
) : AnimalRepository {
    private val queries = db.animalDbQueries

    override suspend fun searchAnimals(query: String): List<Animal> {
        logger.d("AnimalRepo", "Fetching animals with query: $query")
        val response = remoteDataSource.searchPhotos(query)
        return try {
            response.results.map { result ->
                Animal(
                    id = result.id,
                    imageUrl = result.urls.regular,
                    description = result.description ?: "No description available",
                    name = result.name ?: "No name available"
                )
            }
        } catch (e: Exception) {
            logger.e("AnimalRepo", "Error fetching animals", e)
            emptyList()
        }
    }

    override fun toogleFavorite(animal: Animal) {
        val isFav = queries.selectFavoriteById(animal.id).executeAsOneOrNull() != null
        if (isFav) {
            queries.deleteFavorite(animal.id)
        } else {
            queries.insertFavorite(
                id = animal.id,
                name = animal.name,
                description = animal.description,
                imageUrl = animal.imageUrl,
                createdAt = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
            )
        }
    }
    override fun isFavorite(id: String): Flow<Boolean> {
        return queries.selectFavoriteById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it != null }
    }
}

fun UnsplashPhotoDto.toDomain(): Animal {
    return Animal(
        id = this.id,
        imageUrl = this.urls.regular,
        name = this.name ?: "No name available",
        description = this.description ?: "No description available"
    )
}

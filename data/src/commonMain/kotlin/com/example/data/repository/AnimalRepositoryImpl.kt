package com.example.data.repository

import com.example.data.remote.UnsplashRemoteDataSource
import com.example.data.remote.models.UnsplashImageDto
import com.example.domain.model.Animal
import com.example.domain.repository.AnimalRepository
import com.example.domain.util.AppLogger

class AnimalRepositoryImpl(
    private val remoteDataSource: UnsplashRemoteDataSource,
    private val logger: AppLogger
) : AnimalRepository {

    override suspend fun getAnimalImages(query: String): List<Animal> {
        logger.d("AnimalRepo", "Fetching animals with query: $query")
        val response = remoteDataSource.searchAnimalImages(query)
        return try {
            response.results.map { result ->
                Animal(
                    id = result.id,
                    description = result.description ?: "Unknown description",
                    imageUrl = result.urls.regular,
                    name = result.name ?: "Unknown Animal",
                    tags = result.tags

                )
            }
        }catch (e: Exception){
            logger.e("AnimalRepo", "Error fetching animals", e)
            emptyList()
        }
    }
}

private fun UnsplashImageDto.toDomain(): Animal {
    return Animal(
        id = this.id,
        imageUrl = this.urls.regular,
        description = this.description ?: "Animal Image",
        name = this.name ?: "Unknown Animal",
        tags = this.tags
    )
}

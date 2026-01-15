package com.example.data.repository

import com.example.data.remote.UnsplashRemoteDataSource
import com.example.data.remote.models.UnsplashImageDto
import com.example.domain.model.AnimalImage
import com.example.domain.repository.AnimalRepository

class AnimalRepositoryImpl(
    private val remoteDataSource: UnsplashRemoteDataSource
) : AnimalRepository {

    override suspend fun getAnimalImages(animalType: String): List<AnimalImage> {
        val response = remoteDataSource.searchAnimalImages(animalType)
        return response.results.map { dto ->
            dto.toDomain()
        }
    }
}

private fun UnsplashImageDto.toDomain(): AnimalImage {
    return AnimalImage(
        id = this.id,
        imageUrl = this.urls.regular,
        description = this.description ?: "Animal Image"
    )
}

package com.example.data.remote.models

import com.example.domain.model.Animal
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashResponse(
    val results: List<UnsplashImageDto>
)

@Serializable
data class UnsplashImageDto(
    val id: String,
    val name: String?,
    val tags: List<String>,
    val urls: ImageUrlsDto,
    val description: String?
)

@Serializable
data class ImageUrlsDto(
    val regular: String,
    val small: String
)

fun UnsplashImageDto.toDomain(): Animal = Animal(
    id = this.id,
    name = this.name ?: "Pet",
    imageUrl = this.urls.regular,
    description = this.description,
    tags = this.tags
)

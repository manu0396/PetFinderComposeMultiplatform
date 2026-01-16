package com.example.data.remote.models

import com.example.domain.model.Animal
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashResponseDto(
    @SerialName("results") val results: List<UnsplashPhotoDto> = emptyList(),
    @SerialName("total") val total: Int? = 0,
    @SerialName("total_pages") val totalPages: Int = 0
)

@Serializable
data class UnsplashPhotoDto(
    val id: String,
    @SerialName("urls") val urls: UnsplashUrlsDto,
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null
)

@Serializable
data class UnsplashUrlsDto(
    @SerialName("regular") val regular: String,
    @SerialName("small") val small: String,
    @SerialName("thumb") val thumb: String
)

fun UnsplashPhotoDto.toDomain(): Animal = Animal(
    id = this.id,
    imageUrl = this.urls.regular,
    name = this.name ?: "No name available",
    description = this.description ?: "No description available"
)

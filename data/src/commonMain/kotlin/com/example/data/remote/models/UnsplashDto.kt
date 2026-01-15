package com.example.data.remote.models

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

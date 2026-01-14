package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: String,
    val url: String,
    val author: String? = null
)

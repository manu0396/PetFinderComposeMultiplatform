package com.example.data.repository

import com.example.data.models.ImageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class ImageRepository(private val client: HttpClient) {
    fun getImages(): Flow<List<ImageDto>> = flow {
        val response = client.get("https://api.example.com/images").body<List<ImageDto>>()
        emit(response)
    }
}

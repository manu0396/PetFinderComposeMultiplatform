package com.example.data.remote

import com.example.data.mapper.toDomainError
import com.example.data.remote.models.UnsplashResponseDto
import com.example.petfinder.data.BuildKonfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class UnsplashRemoteDataSource(
    private val client: HttpClient
) {

    suspend fun searchPhotos(query: String): UnsplashResponseDto {
        return try {
            val response = client.get("search/photos") {
                parameter("query", query)
                parameter("per_page", 20)
            }

            response.body<UnsplashResponseDto>()

        } catch (e: Exception) {
            println("!!! [ERROR] API Failure in DataSource: ${e.message}")
            throw e.toDomainError()
        }
    }
}

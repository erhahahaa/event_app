package dev.erhahahaa.eventapp.di

import dev.erhahahaa.eventapp.data.api.ApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ApiServiceFactory {
  private const val BASE_URL = "https://event-api.dicoding.dev"

  fun create(): ApiService {
    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }

    val mediaType =
      MediaType.parse("application/json") ?: throw IllegalArgumentException("Invalid media type")

    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(json.asConverterFactory(mediaType))
      .build()

    return retrofit.create(ApiService::class.java)
  }
}
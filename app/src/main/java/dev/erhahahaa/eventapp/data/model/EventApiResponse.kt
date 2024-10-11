package dev.erhahahaa.eventapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EventApiResponse<T>(
  val error: Boolean,
  val message: String,
  val event: T? = null,
  val listEvents: T? = null,
)

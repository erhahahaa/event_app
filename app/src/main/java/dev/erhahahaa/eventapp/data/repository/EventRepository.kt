package dev.erhahahaa.eventapp.data.repository

import dev.erhahahaa.eventapp.data.api.ApiService
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import retrofit2.Call

enum class EventStatus(val value: Int) {
  ALL(-1),
  ACTIVE(1),
  FINISHED(0),
}

class EventRepository(apiService: ApiService) {
  private val api: ApiService = apiService

  fun getAllEvents(status: EventStatus): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(status.value)
  }

  fun getEventDetail(eventId: Int): Call<EventApiResponse<Event>> {
    return api.getEventDetail(eventId)
  }

  fun searchEvents(
    status: EventStatus = EventStatus.ALL,
    query: String,
  ): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(status.value, query)
  }
}

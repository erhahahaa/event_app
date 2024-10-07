package dev.erhahahaa.eventapp.data.repository

import dev.erhahahaa.eventapp.data.api.ApiService
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import retrofit2.Call

class EventRepository(apiService: ApiService) {
  private val api: ApiService = apiService

  fun getAllEvents(): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(-1)
  }

  fun getActiveEvents(): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(1)
  }

  fun getFinishedEvents(): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(0)
  }

  fun getEventDetail(eventId: Int): Call<EventApiResponse<Event>> {
    return api.getEventDetail(eventId)
  }

  fun searchEvents(query: String): Call<EventApiResponse<List<Event>>> {
    return api.getEvents(-1, query)
  }
}
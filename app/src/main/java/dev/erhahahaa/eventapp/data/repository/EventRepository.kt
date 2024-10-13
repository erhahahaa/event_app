package dev.erhahahaa.eventapp.data.repository

import dev.erhahahaa.eventapp.data.api.ApiService
import dev.erhahahaa.eventapp.data.dao.FavoriteEventDao
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import dev.erhahahaa.eventapp.data.model.FavoriteEvent
import retrofit2.Call

enum class EventStatus(val value: Int) {
  ALL(-1),
  ACTIVE(1),
  FINISHED(0),
}

class EventRepository(private val api: ApiService, private val favoriteEventDao: FavoriteEventDao) {

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

  suspend fun addFavorite(event: FavoriteEvent) {
    favoriteEventDao.addFavorite(event)
  }

  suspend fun removeFavorite(eventId: Int) {
    favoriteEventDao.removeFavorite(eventId)
  }

  suspend fun getAllFavorites(): List<FavoriteEvent> {
    return favoriteEventDao.getAllFavorites()
  }

  suspend fun clearFavorites() {
    favoriteEventDao.clearFavorites()
  }
}

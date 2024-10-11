package dev.erhahahaa.eventapp.data.api

import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("/events")
  fun getEvents(
    @Query("active") active: Int? = null,
    @Query("q") query: String? = null,
    @Query("limit") limit: Int = 10,
  ): Call<EventApiResponse<List<Event>>>

  @GET("/events/{id}") fun getEventDetail(@Path("id") id: Int): Call<EventApiResponse<Event>>
}

package dev.erhahahaa.eventapp.data.repository

import dev.erhahahaa.eventapp.data.api.ApiService
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response

class EventRepositoryTest {

  private val apiService = mock(ApiService::class.java)
  private val repository = EventRepository(apiService)
  private val event =
    Event(
      id = 8933,
      name =
        "DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas",
      summary =
        "Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube",
      description = "super long html description",
      imageLogo =
        "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_logo_041024134406.png",
      mediaCover =
        "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_mc_041024134407.jpg",
      category = "Seminar",
      ownerName = "Dicoding Event",
      cityName = "Online",
      quota = 2000,
      registrants = 452,
      beginTime = "2024-10-11 16:00:00",
      endTime = "2024-10-11 17:00:00",
      link = "https://www.dicoding.com/events/8933",
    )

  @Test
  fun testGetAllEventsReturnsEvents() {
    val call = mock(Call::class.java) as Call<EventApiResponse<List<Event>>>
    `when`(apiService.getEvents(-1)).thenReturn(call)
    val response =
      Response.success(
        EventApiResponse(
          error = false,
          message = "Events fetched successfully",
          listEvents = listOf(event),
        )
      )

    `when`(call.execute()).thenReturn(response)

    val result = repository.getAllEvents(EventStatus.ALL).execute()
    assertEquals(true, result.isSuccessful)
    assertEquals(true, result.body()?.listEvents?.isNotEmpty())

    val firstEvent = result.body()?.listEvents?.first()
    assertEquals(firstEvent?.id, event.id)
    assertEquals(firstEvent?.name, event.name)
    assertEquals(firstEvent?.category, event.category)
    assertEquals(firstEvent?.cityName, event.cityName)
    assertEquals(firstEvent?.quota, event.quota)
    assertEquals(firstEvent?.registrants, event.registrants)
    assertEquals(firstEvent?.beginTime, event.beginTime)
    assertEquals(firstEvent?.endTime, event.endTime)
    assertEquals(firstEvent?.link, event.link)
  }

  @Test
  fun testGetActiveEventsReturnsEvents() {
    val call = mock(Call::class.java) as Call<EventApiResponse<List<Event>>>
    `when`(apiService.getEvents(1)).thenReturn(call)
    val response =
      Response.success(
        EventApiResponse(
          error = false,
          message = "Events fetched successfully",
          listEvents = listOf(event),
        )
      )

    `when`(call.execute()).thenReturn(response)

    val result = repository.getAllEvents(EventStatus.ACTIVE).execute()
    assertEquals(true, result.isSuccessful)
    assertEquals(true, result.body()?.listEvents?.isNotEmpty())

    val firstEvent = result.body()?.listEvents?.first()
    assertEquals(firstEvent?.id, event.id)
    assertEquals(firstEvent?.name, event.name)
    assertEquals(firstEvent?.category, event.category)
    assertEquals(firstEvent?.cityName, event.cityName)
    assertEquals(firstEvent?.quota, event.quota)
    assertEquals(firstEvent?.registrants, event.registrants)
    assertEquals(firstEvent?.beginTime, event.beginTime)
    assertEquals(firstEvent?.endTime, event.endTime)
    assertEquals(firstEvent?.link, event.link)
  }

  @Test
  fun testGetFinishedEventsReturnsEvents() {
    val call = mock(Call::class.java) as Call<EventApiResponse<List<Event>>>
    `when`(apiService.getEvents(0)).thenReturn(call)
    val response =
      Response.success(
        EventApiResponse(
          error = false,
          message = "Events fetched successfully",
          listEvents = listOf(event),
        )
      )

    `when`(call.execute()).thenReturn(response)

    val result = repository.getAllEvents(EventStatus.FINISHED).execute()
    assertEquals(true, result.isSuccessful)
    assertEquals(true, result.body()?.listEvents?.isNotEmpty())

    val firstEvent = result.body()?.listEvents?.first()
    assertEquals(firstEvent?.id, event.id)
    assertEquals(firstEvent?.name, event.name)
    assertEquals(firstEvent?.category, event.category)
    assertEquals(firstEvent?.cityName, event.cityName)
    assertEquals(firstEvent?.quota, event.quota)
    assertEquals(firstEvent?.registrants, event.registrants)
    assertEquals(firstEvent?.beginTime, event.beginTime)
    assertEquals(firstEvent?.endTime, event.endTime)
    assertEquals(firstEvent?.link, event.link)
  }

  @Test
  fun testGetEventDetailReturnsEvent() {
    val call = mock(Call::class.java) as Call<EventApiResponse<Event>>
    `when`(apiService.getEventDetail(event.id)).thenReturn(call)
    val response =
      Response.success(
        EventApiResponse(error = false, message = "Event fetched successfully", event = event)
      )

    `when`(call.execute()).thenReturn(response)

    val result = repository.getEventDetail(event.id).execute()
    assertEquals(true, result.isSuccessful)
    assertEquals(false, result.body()?.error)

    val fetchedEvent = result.body()?.event
    assertEquals(fetchedEvent?.id, event.id)
    assertEquals(fetchedEvent?.name, event.name)
    assertEquals(fetchedEvent?.category, event.category)
    assertEquals(fetchedEvent?.cityName, event.cityName)
    assertEquals(fetchedEvent?.quota, event.quota)
    assertEquals(fetchedEvent?.registrants, event.registrants)
    assertEquals(fetchedEvent?.beginTime, event.beginTime)
    assertEquals(fetchedEvent?.endTime, event.endTime)
    assertEquals(fetchedEvent?.link, event.link)
  }

  @Test
  fun testGearchEventsReturnsEvents() {
    val call = mock(Call::class.java) as Call<EventApiResponse<List<Event>>>
    val query = "DevCoach"
    `when`(apiService.getEvents(-1, query)).thenReturn(call)
    val response =
      Response.success(
        EventApiResponse(
          error = false,
          message = "Events fetched successfully",
          listEvents = listOf(event),
        )
      )

    `when`(call.execute()).thenReturn(response)

    val result = repository.searchEvents(query).execute()
    assertEquals(true, result.isSuccessful)
    assertEquals(true, result.body()?.listEvents?.isNotEmpty())

    val firstEvent = result.body()?.listEvents?.first()
    assertEquals(firstEvent?.id, event.id)
    assertEquals(firstEvent?.name, event.name)
    assertEquals(firstEvent?.category, event.category)
    assertEquals(firstEvent?.cityName, event.cityName)
    assertEquals(firstEvent?.quota, event.quota)
    assertEquals(firstEvent?.registrants, event.registrants)
    assertEquals(firstEvent?.beginTime, event.beginTime)
    assertEquals(firstEvent?.endTime, event.endTime)
    assertEquals(firstEvent?.link, event.link)
  }

  @Test
  fun testGetEventsReturnsError() {
    val call = mock(Call::class.java) as Call<EventApiResponse<List<Event>>>
    `when`(apiService.getEvents(-1)).thenReturn(call)

    val errorResponseBody = ResponseBody.create(null, "")
    val response = Response.error<EventApiResponse<List<Event>>>(404, errorResponseBody)

    `when`(call.execute()).thenReturn(response)

    val result = repository.getAllEvents(EventStatus.ALL).execute()

    assertEquals(false, result.isSuccessful)
    assertEquals("", result.errorBody()?.string())
  }

  @Test
  fun testGetEventDetailReturnsError() {
    val call = mock(Call::class.java) as Call<EventApiResponse<Event>>
    `when`(apiService.getEventDetail(event.id)).thenReturn(call)

    val errorResponseBody = ResponseBody.create(null, "")
    val response = Response.error<EventApiResponse<Event>>(404, errorResponseBody)

    `when`(call.execute()).thenReturn(response)

    val result = repository.getEventDetail(event.id).execute()

    assertEquals(false, result.isSuccessful)
    assertEquals("", result.errorBody()?.string())
  }
}

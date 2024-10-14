package dev.erhahahaa.eventapp.data.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class EventApiResponseTest {

  private val jsonEvent =
    """{"error":false,"message":"event fetch successfully","event":{"id":8933,"name":"DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas","summary":"Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube","description":"super long html description","imageLogo":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_logo_041024134406.png","mediaCover":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_mc_041024134407.jpg","category":"Seminar","ownerName":"Dicoding Event","cityName":"Online","quota":2000,"registrants":452,"beginTime":"2024-10-11 16:00:00","endTime":"2024-10-11 17:00:00","link":"https://www.dicoding.com/events/8933"}}"""

  @Test
  fun testEventApiResponseDeserialization() {
    val json = Json { ignoreUnknownKeys = true }
    val eventApiResponse = json.decodeFromString<EventApiResponse<Event>>(jsonEvent)

    assertFalse(eventApiResponse.error)
    assertEquals("event fetch successfully", eventApiResponse.message)
    assertEquals(8933, eventApiResponse.event?.id)
    assertEquals(
      "DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas",
      eventApiResponse.event?.name,
    )
    assertEquals("Seminar", eventApiResponse.event?.category)
    assertEquals("Online", eventApiResponse.event?.cityName)
    assertEquals(2000, eventApiResponse.event?.quota)
    assertEquals(452, eventApiResponse.event?.registrants)
    assertEquals("2024-10-11 16:00:00", eventApiResponse.event?.beginTime)
    assertEquals("2024-10-11 17:00:00", eventApiResponse.event?.endTime)
    assertEquals("https://www.dicoding.com/events/8933", eventApiResponse.event?.link)
  }

  @Test
  fun testEventApiResponseSerialization() {
    val event =
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
    val eventApiResponse =
      EventApiResponse(error = false, message = "event fetch successfully", event = event)

    val json = Json.encodeToString(eventApiResponse)

    assertEquals(jsonEvent, json)
  }

  @Test
  fun testMissingField() {
    val jsonEventMissingField =
      """{"error":false,"message":"event fetch successfully","event":{"id":8933,"name":"DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas","summary":"Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube","description":"super long html description","imageLogo":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_logo_041024134406.png","mediaCover":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_mc_041024134407.jpg","category":"Seminar","ownerName":"Dicoding Event","cityName":"Online","quota":2000,"registrants":452,"beginTime":"2024-10-11 16:00:00","endTime":"2024-10-11 17:00:00"}}"""

    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }
    val eventApiResponse = json.decodeFromString<EventApiResponse<Event>>(jsonEventMissingField)

    assertEquals(null, eventApiResponse.event?.link)
  }

  @Test
  fun testNotFoundField() {
    val jsonEventNotFoundField = """{"error":true,"message":"Event not found"}"""

    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }
    val eventApiResponse = json.decodeFromString<EventApiResponse<Event>>(jsonEventNotFoundField)

    assertEquals(true, eventApiResponse.error)
    assertEquals("Event not found", eventApiResponse.message)
    assertEquals(null, eventApiResponse.event)
  }
}

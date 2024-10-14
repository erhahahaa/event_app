package dev.erhahahaa.eventapp.data.model

import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class EventTest {

  private val jsonEvent =
    """{"id":8933,"name":"DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas","summary":"Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube","description":"super long html description","imageLogo":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_logo_041024134406.png","mediaCover":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_mc_041024134407.jpg","category":"Seminar","ownerName":"Dicoding Event","cityName":"Online","quota":2000,"registrants":452,"beginTime":"2024-10-11 16:00:00","endTime":"2024-10-11 17:00:00","link":"https://www.dicoding.com/events/8933"}"""

  @Test
  fun testEventDeserialization() {
    val json = Json { ignoreUnknownKeys = true }
    val event = json.decodeFromString<Event>(jsonEvent)

    assertEquals(8933, event.id)
    assertEquals(
      "DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas",
      event.name,
    )
    assertEquals("Seminar", event.category)
    assertEquals("Online", event.cityName)
    assertEquals(2000, event.quota)
    assertEquals(452, event.registrants)
    assertEquals("2024-10-11 16:00:00", event.beginTime)
    assertEquals("2024-10-11 17:00:00", event.endTime)
    assertEquals("https://www.dicoding.com/events/8933", event.link)
  }

  @Test
  fun testEventSerialization() {
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

    val json = Json.encodeToString(event)
    assertEquals(jsonEvent, json)
  }

  @Test
  fun testMissingField() {
    val jsonEventMissingField =
      """{"id":8933,"name":"DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas","summary":"Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube","description":"super long html description","imageLogo":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_logo_041024134406.png","mediaCover":"https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/event/dos-devcoach_172_flutter_tingkatkan_pengalaman_pengguna_dengan_lokalisasi_dan_aksesibilitas_mc_041024134407.jpg","category":"Seminar","ownerName":"Dicoding Event","cityName":"Online","quota":2000,"registrants":452,"beginTime":"2024-10-11 16:00:00","endTime":"2024-10-11 17:00:00"}"""

    val json = Json {
      ignoreUnknownKeys = true
      explicitNulls = false
    }
    val event = json.decodeFromString<Event>(jsonEventMissingField)

    assertEquals(null, event.link)
  }

  @Test
  fun testMissingRequiredField() {
    val missingRequiredFieldJson =
      """{"name":"DevCoach 172: Flutter | Tingkatkan Pengalaman Pengguna dengan Lokalisasi dan Aksesibilitas","summary":"Acara ini sepenuhnya GRATIS dan akan diselenggarakan hari Jumat, 11 Oktober 2024 pukul 16.00 - 17.00 WIB Live di YouTube"}"""

    val json = Json { ignoreUnknownKeys = true }

    assertThrows(SerializationException::class.java) {
      json.decodeFromString<Event>(missingRequiredFieldJson)
    }
  }

  @Test
  fun testIncorrectDataFormat() {
    val incorrectDataFormatJson =
      """{"id":8933,"name":true,"summary":null,"description":"super long html description","imageLogo":null,"mediaCover":null,"category":null,"ownerName":null,"cityName":null,"quota":null,"registrants":null,"beginTime":null,"endTime":null,"link":null}"""

    val json = Json { ignoreUnknownKeys = true }

    assertThrows(SerializationException::class.java) {
      json.decodeFromString<Event>(incorrectDataFormatJson)
    }
  }
}

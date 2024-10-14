package dev.erhahahaa.eventapp.di

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull

class ApiServiceFactoryTest {

  @Test
  fun createApiService() {
    val apiService = ApiServiceFactory.create()
    assertNotNull(apiService)
  }
}

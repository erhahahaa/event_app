package dev.erhahahaa.eventapp.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

  private val themeKey = booleanPreferencesKey("theme_setting")

  fun getTheme(): Flow<Boolean?> {
    return dataStore.data.map { preferences -> preferences[themeKey] }
  }

  suspend fun setTheme(isDarkTheme: Boolean?) {
    isDarkTheme?.let {
      dataStore.edit { preferences -> preferences[themeKey] = it }
      AppCompatDelegate.setDefaultNightMode(
        if (it) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
      )
    }
  }

  companion object {
    @Volatile private var INSTANCE: SettingPreferences? = null

    fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
      return INSTANCE
        ?: synchronized(this) {
          val instance = SettingPreferences(dataStore)
          INSTANCE = instance
          instance
        }
    }
  }
}

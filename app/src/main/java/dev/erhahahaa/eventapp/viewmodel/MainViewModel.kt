package dev.erhahahaa.eventapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.erhahahaa.eventapp.data.SettingPreferences
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
  fun getThemes(): LiveData<Boolean?> {
    return pref.getTheme().asLiveData()
  }

  fun setTheme(isDarkModeActive: Boolean) {
    viewModelScope.launch { pref.setTheme(isDarkModeActive) }
  }

  fun getNotification(): LiveData<Boolean> {
    return pref.getNotification().asLiveData()
  }

  fun setNotification(isNotificationActive: Boolean) {
    viewModelScope.launch { pref.setNotification(isNotificationActive) }
  }
}

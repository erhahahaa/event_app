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

  fun saveTheme(isDarkModeActive: Boolean) {
    viewModelScope.launch { pref.setTheme(isDarkModeActive) }
  }
}

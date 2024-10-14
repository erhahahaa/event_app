package dev.erhahahaa.eventapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import dev.erhahahaa.eventapp.data.SettingPreferences
import dev.erhahahaa.eventapp.viewmodel.MainViewModel

class ViewModelFactory(private val pref: SettingPreferences) : NewInstanceFactory() {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
      return MainViewModel(pref) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }
}

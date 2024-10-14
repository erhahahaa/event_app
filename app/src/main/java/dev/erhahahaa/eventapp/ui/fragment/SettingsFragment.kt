package dev.erhahahaa.eventapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dev.erhahahaa.eventapp.data.SettingPreferences
import dev.erhahahaa.eventapp.databinding.FragmentSettingsBinding
import dev.erhahahaa.eventapp.di.ViewModelFactory
import dev.erhahahaa.eventapp.utils.extension.dataStore
import dev.erhahahaa.eventapp.viewmodel.MainViewModel

class SettingsFragment : Fragment() {

  private val dataStore: DataStore<Preferences>
    get() = requireContext().dataStore

  private var _binding: FragmentSettingsBinding? = null
  private val binding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentSettingsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val pref = SettingPreferences.getInstance(dataStore)

    val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

    mainViewModel.getThemes().observe(viewLifecycleOwner) { isDarkModeActive ->
      binding.themeSwitch.isChecked = isDarkModeActive ?: false
    }

    binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
      mainViewModel.saveTheme(isChecked)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}

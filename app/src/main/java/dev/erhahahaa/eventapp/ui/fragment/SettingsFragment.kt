package dev.erhahahaa.eventapp.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dev.erhahahaa.eventapp.databinding.FragmentSettingsBinding
import dev.erhahahaa.eventapp.viewmodel.MainViewModel

class SettingsFragment : Fragment() {

  private val mainViewModel: MainViewModel by activityViewModels()

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

    mainViewModel.getThemes().observe(viewLifecycleOwner) { isDarkModeActive ->
      binding.themeSwitch.isChecked = isDarkModeActive ?: false
    }

    mainViewModel.getNotification().observe(viewLifecycleOwner) { isNotificationActive ->
      binding.notificationSwitch.isChecked = isNotificationActive
    }

    binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
      mainViewModel.setTheme(isChecked)
    }

    binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
          if (
            ContextCompat.checkSelfPermission(
              requireContext(),
              Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
          ) {
            mainViewModel.setNotification(true)
          } else {
            requestNotificationPermission()
          }
        } else {
          mainViewModel.setNotification(true)
        }
      } else {
        mainViewModel.setNotification(false)
      }
    }
  }

  private val notificationPermissionLauncher =
    this.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        mainViewModel.setNotification(true)
      } else {
        mainViewModel.setNotification(false)
      }
    }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  private fun requestNotificationPermission() {
    if (
      ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) !=
        PackageManager.PERMISSION_GRANTED
    ) {
      notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}

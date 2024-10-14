package dev.erhahahaa.eventapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.erhahahaa.eventapp.R
import dev.erhahahaa.eventapp.data.SettingPreferences
import dev.erhahahaa.eventapp.databinding.ActivityMainBinding
import dev.erhahahaa.eventapp.di.ViewModelFactory
import dev.erhahahaa.eventapp.utils.DailyReminderWorker
import dev.erhahahaa.eventapp.utils.extension.dataStore
import dev.erhahahaa.eventapp.viewmodel.MainViewModel
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController
  private lateinit var pref: SettingPreferences
  private lateinit var mainViewModel: MainViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    pref = SettingPreferences.getInstance(dataStore)
    mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]
    mainViewModel.getThemes().observe(this) { isDarkModeActive ->
      if (isDarkModeActive != null) {
        mainViewModel.setTheme(isDarkModeActive)
      }
    }

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    navController = navHostFragment.navController

    binding.bottomNavigationView.setupWithNavController(navController)

    navController.addOnDestinationChangedListener { _, destination, _ ->
      if (destination.id == R.id.eventDetailFragment) {
        binding.bottomNavigationView.visibility = android.view.View.GONE
      } else {
        binding.bottomNavigationView.visibility = android.view.View.VISIBLE
      }
    }

    setupDailyReminder()

    val eventId = intent.getIntExtra("eventId", -1)
    if (eventId != -1) {
      val bundle = Bundle().apply { putInt("eventId", eventId) }
      navController.navigate(R.id.eventDetailFragment, bundle)
    }
  }

  private fun setupDailyReminder() {
    val workRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.DAYS).build()
    WorkManager.getInstance(this)
      .enqueueUniquePeriodicWork(
        "EventAppDailyReminder",
        ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
        workRequest,
      )
  }

  override fun onDestroy() {
    super.onDestroy()
    binding.bottomNavigationView.setOnItemSelectedListener(null)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }
}

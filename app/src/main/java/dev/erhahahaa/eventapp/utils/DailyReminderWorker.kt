package dev.erhahahaa.eventapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.erhahahaa.eventapp.data.SettingPreferences
import dev.erhahahaa.eventapp.data.api.ApiService
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.di.ApiServiceFactory
import dev.erhahahaa.eventapp.ui.activity.MainActivity
import dev.erhahahaa.eventapp.utils.extension.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class DailyReminderWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
  private val apiService: ApiService = ApiServiceFactory.create()

  override suspend fun doWork(): Result {
    if (!isNotificationEnabled()) {
      Log.d(TAG, "Notifications are disabled. Cancelling operation.")
      return Result.success()
    }

    Log.d(TAG, "DailyReminderWorker: doWork")
    return try {
      val response = apiService.getEvents(active = -1, limit = 1).execute()
      if (response.isSuccessful) {
        val body = response.body()
        val events = body?.listEvents
        if (!events.isNullOrEmpty()) {
          val event = events[0]
          sendNotification(event)
          Log.d(TAG, "API call successful, event: $event")
        }
        Result.success()
      } else {
        Result.retry()
      }
    } catch (e: Exception) {
      Log.e(TAG, "Error during API call", e)
      Result.failure()
    }
  }

  private fun isNotificationEnabled(): Boolean {
    val dataStore = applicationContext.dataStore
    val prefs: SettingPreferences = SettingPreferences.getInstance(dataStore)
    return runBlocking { prefs.getNotification().first() }
  }

  private fun sendNotification(event: Event) {
    val notificationManager =
      applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "daily_reminder_channel"

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      val channel =
        NotificationChannel(
          channelId,
          "Event App Daily Reminder",
          NotificationManager.IMPORTANCE_HIGH,
        )
      notificationManager.createNotificationChannel(channel)
    }

    val intent =
      Intent(applicationContext, MainActivity::class.java).apply {
        putExtra("eventId", event.id)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      }

    val pendingIntent =
      PendingIntent.getActivity(
        applicationContext,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
      )

    val notification =
      NotificationCompat.Builder(applicationContext, channelId)
        .setContentTitle("Event App Daily Reminder")
        .setContentText("Event: ${event.name} at ${event.beginTime}")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(1, notification)
  }

  companion object {
    private const val TAG = "DailyReminderWorker"
  }
}

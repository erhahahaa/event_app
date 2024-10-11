package dev.erhahahaa.eventapp.utils

import android.os.Handler
import android.os.Looper

class Debounce(private val delayMillis: Long) {
  private var handler: Handler = Handler(Looper.getMainLooper())
  private var runnable: Runnable? = null

  fun debounce(action: () -> Unit) {
    runnable?.let { handler.removeCallbacks(it) }
    runnable = Runnable { action() }
    handler.postDelayed(runnable!!, delayMillis)
  }
}

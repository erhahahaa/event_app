package dev.erhahahaa.eventapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.erhahahaa.eventapp.data.dao.FavoriteEventDao
import dev.erhahahaa.eventapp.data.model.FavoriteEvent

@Database(entities = [FavoriteEvent::class], version = 1)
abstract class DatabaseRegistrar : RoomDatabase() {
  abstract fun favoriteEventDao(): FavoriteEventDao

  companion object {
    @Volatile private var INSTANCE: DatabaseRegistrar? = null

    @JvmStatic
    fun getDatabase(context: Context): DatabaseRegistrar {
      if (INSTANCE == null) {
        synchronized(DatabaseRegistrar::class.java) {
          INSTANCE =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseRegistrar::class.java,
                "event_app_db",
              )
              .build()
        }
      }
      return INSTANCE as DatabaseRegistrar
    }
  }
}

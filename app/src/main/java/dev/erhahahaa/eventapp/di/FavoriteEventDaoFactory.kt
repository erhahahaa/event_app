package dev.erhahahaa.eventapp.di

import android.content.Context
import dev.erhahahaa.eventapp.data.dao.FavoriteEventDao
import dev.erhahahaa.eventapp.data.db.DatabaseRegistrar

class FavoriteEventDaoFactory {
  fun create(context: Context): FavoriteEventDao {
    val db = DatabaseRegistrar.getDatabase(context = context)
    return db.favoriteEventDao()
  }
}

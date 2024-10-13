package dev.erhahahaa.eventapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.erhahahaa.eventapp.data.model.FavoriteEvent

@Dao
interface FavoriteEventDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun addFavorite(event: FavoriteEvent)

  @Query("DELETE FROM favorite_events WHERE eventId = :eventId")
  suspend fun removeFavorite(eventId: Int)

  @Query("SELECT * FROM favorite_events") suspend fun getAllFavorites(): List<FavoriteEvent>

  @Query("DELETE FROM favorite_events") suspend fun clearFavorites()
}

package dev.erhahahaa.eventapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_events")
data class FavoriteEvent(
  @PrimaryKey(autoGenerate = false) val eventId: Int,
  val name: String,
  val ownerName: String,
  val imageLogo: String?,
  val mediaCover: String?,
  val beginTime: String?,
)

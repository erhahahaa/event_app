package dev.erhahahaa.eventapp.utils

import androidx.recyclerview.widget.DiffUtil
import dev.erhahahaa.eventapp.data.model.FavoriteEvent

class FavoriteEventDiffCallback(
  private val oldList: List<FavoriteEvent>,
  private val newList: List<FavoriteEvent>,
) : DiffUtil.Callback() {
  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition].eventId == newList[newItemPosition].eventId
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition] == newList[newItemPosition]
  }
}

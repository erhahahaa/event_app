package dev.erhahahaa.eventapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.databinding.HorizontalItemEventBinding
import dev.erhahahaa.eventapp.databinding.VerticalItemEventBinding

enum class ViewDirection {
  HORIZONTAL,
  VERTICAL,
}

class EventAdapter(
  private val onItemClick: (Int) -> Unit,
  private val direction: ViewDirection = ViewDirection.HORIZONTAL,
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

  private var eventList: List<Event> = listOf()

  fun submitList(events: List<Event>?) {
    if (events == null) return
    eventList = events
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
    val binding =
      if (direction == ViewDirection.HORIZONTAL) {
        HorizontalItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      } else {
        VerticalItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      }
    return EventViewHolder(binding, direction)
  }

  override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
    val event = eventList[position]
    holder.bind(event, onItemClick)
  }

  override fun getItemCount(): Int = eventList.size

  class EventViewHolder(private val binding: Any, private val direction: ViewDirection) :
    RecyclerView.ViewHolder(
      (binding as? HorizontalItemEventBinding)?.root ?: (binding as VerticalItemEventBinding).root
    ) {

    fun bind(event: Event, onItemClick: (Int) -> Unit) {
      when (direction) {
        ViewDirection.HORIZONTAL -> {
          val horizontalBinding = binding as HorizontalItemEventBinding
          horizontalBinding.tvEventName.text = event.name
          horizontalBinding.tvEventOwner.text = event.ownerName
          horizontalBinding.tvEventTime.text = event.beginTime

          Glide.with(horizontalBinding.root.context)
            .load(event.mediaCover ?: event.imageLogo)
            .into(horizontalBinding.ivEventImage)

          horizontalBinding.root.setOnClickListener { onItemClick(event.id) }
        }
        ViewDirection.VERTICAL -> {
          val verticalBinding = binding as VerticalItemEventBinding
          verticalBinding.tvEventName.text = event.name

          Glide.with(verticalBinding.root.context)
            .load(event.mediaCover ?: event.imageLogo)
            .into(verticalBinding.ivEventImage)

          verticalBinding.root.setOnClickListener { onItemClick(event.id) }
        }
      }
    }
  }
}

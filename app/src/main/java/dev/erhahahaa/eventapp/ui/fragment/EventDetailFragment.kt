package dev.erhahahaa.eventapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dev.erhahahaa.eventapp.R
import dev.erhahahaa.eventapp.databinding.FragmentEventDetailBinding
import dev.erhahahaa.eventapp.viewmodel.EventViewModel

class EventDetailFragment : Fragment() {

  private var _binding: FragmentEventDetailBinding? = null
  private val binding
    get() = _binding!!

  private lateinit var eventViewModel: EventViewModel
  private var eventId: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let { eventId = it.getInt("eventId") }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

    eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

    eventViewModel.detail.observe(viewLifecycleOwner) { event ->
      event?.let {
        updateFavoriteIcon(it.id)

        binding.toolbar.title = it.name
        binding.tvEventName.text = it.name
        binding.tvEventOwner.text = it.ownerName
        binding.tvEventTime.text = it.beginTime
        val quota = it.quota - (it.registrants ?: 0)
        binding.tvEventQuota.text = getString(R.string.quota_estimation, quota)
        binding.tvEventDescription.text =
          HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

        Glide.with(requireContext()).load(it.mediaCover ?: it.imageLogo).into(binding.ivEventImage)

        binding.btnEventLink.setOnClickListener {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
          startActivity(intent)
        }

        binding.btnFavorite.setOnClickListener {
          if (eventViewModel.isFavorite(event.id)) {
            eventViewModel.removeFavorite(event.id)
            Toast.makeText(
                context,
                "Event ${event.name} has been removed from favorite",
                Toast.LENGTH_SHORT,
              )
              .show()
          } else {
            eventViewModel.addFavorite(event)
            Toast.makeText(
                context,
                "Event ${event.name} has been added to favorite",
                Toast.LENGTH_SHORT,
              )
              .show()
          }
          updateFavoriteIcon(event.id)
        }
      }
    }

    eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    eventViewModel.error.observe(viewLifecycleOwner) { error ->
      if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        eventViewModel.clearError()
      }
    }

    eventViewModel.getEventDetail(eventId)
  }

  private fun updateFavoriteIcon(eventId: Int) {
    val isFavorite = eventViewModel.isFavorite(eventId)
    if (isFavorite) {
      binding.btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
    } else {
      binding.btnFavorite.setImageResource(R.drawable.ic_favorite_outline)
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}

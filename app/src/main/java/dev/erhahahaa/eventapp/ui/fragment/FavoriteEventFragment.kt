package dev.erhahahaa.eventapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.erhahahaa.eventapp.R
import dev.erhahahaa.eventapp.databinding.FragmentEventListBinding
import dev.erhahahaa.eventapp.ui.adapter.FavoriteEventAdapter
import dev.erhahahaa.eventapp.utils.Debounce
import dev.erhahahaa.eventapp.viewmodel.EventViewModel

class FavoriteEventFragment : Fragment() {
  private var _binding: FragmentEventListBinding? = null
  private val binding
    get() = _binding!!

  private lateinit var eventViewModel: EventViewModel
  private lateinit var eventAdapter: FavoriteEventAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentEventListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.toolbar.title = getString(R.string.favorite_events)

    eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

    eventAdapter =
      FavoriteEventAdapter(
        onItemClick = { eventId ->
          val bundle = Bundle().apply { putInt("eventId", eventId) }
          findNavController().navigate(R.id.event_detail_fragment, bundle)
        }
      )

    binding.rvEvent.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = eventAdapter
    }

    eventViewModel.favoriteEvents.observe(viewLifecycleOwner) { events ->
      eventAdapter.submitList(events)
    }

    eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    eventViewModel.error.observe(viewLifecycleOwner) { error ->
      if (error != null) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
      }
    }

    eventViewModel.loadFavoriteEvents()

    val debounce = Debounce(1000L)

    binding.searchView.setOnQueryTextListener(
      object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
          if (query != null) {
            eventViewModel.searchFavorite(query)
          } else {
            eventViewModel.loadFavoriteEvents()
          }
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          if (newText.isNullOrEmpty()) {
            eventViewModel.loadFavoriteEvents()
          } else {
            debounce.debounce { eventViewModel.searchFavorite(newText) }
          }
          return false
        }
      }
    )
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}

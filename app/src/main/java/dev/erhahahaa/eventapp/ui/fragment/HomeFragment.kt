package dev.erhahahaa.eventapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.erhahahaa.eventapp.R
import dev.erhahahaa.eventapp.databinding.FragmentHomeBinding
import dev.erhahahaa.eventapp.ui.adapter.EventAdapter
import dev.erhahahaa.eventapp.ui.adapter.ViewDirection
import dev.erhahahaa.eventapp.viewmodel.EventViewModel

class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null
  private val binding
    get() = _binding!!

  private lateinit var eventViewModel: EventViewModel
  private lateinit var upcomingEventAdapter: EventAdapter
  private lateinit var finishedEventAdapter: EventAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

    upcomingEventAdapter =
      EventAdapter(
        onItemClick = { eventId ->
          val bundle = Bundle().apply { putInt("eventId", eventId) }
          findNavController().navigate(R.id.event_detail_fragment, bundle)
        },
        direction = ViewDirection.VERTICAL,
      )
    finishedEventAdapter =
      EventAdapter(
        onItemClick = { eventId ->
          val bundle = Bundle().apply { putInt("eventId", eventId) }
          findNavController().navigate(R.id.event_detail_fragment, bundle)
        }
      )

    binding.rvUpcomingEvents.apply {
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      adapter = upcomingEventAdapter
    }

    binding.rvFinishedEvents.apply {
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      adapter = finishedEventAdapter
    }

    eventViewModel.activeEvents.observe(viewLifecycleOwner) { events ->
      upcomingEventAdapter.submitList(events?.take(5))
    }

    eventViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
      finishedEventAdapter.submitList(events?.take(5))
    }

    eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
      binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    eventViewModel.loadActiveEvents()
    eventViewModel.loadFinishedEvents()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}

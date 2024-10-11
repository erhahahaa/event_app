package dev.erhahahaa.eventapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.erhahahaa.eventapp.data.model.Event
import dev.erhahahaa.eventapp.data.model.EventApiResponse
import dev.erhahahaa.eventapp.data.repository.EventRepository
import dev.erhahahaa.eventapp.data.repository.EventStatus
import dev.erhahahaa.eventapp.di.ApiServiceFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventViewModel : ViewModel() {

  private val eventRepository = EventRepository(ApiServiceFactory.create())

  private val _events = MutableLiveData<List<Event>?>()
  val events: LiveData<List<Event>?>
    get() = _events

  private val _activeEvents = MutableLiveData<List<Event>?>()
  val activeEvents: LiveData<List<Event>?>
    get() = _activeEvents

  private val _finishedEvents = MutableLiveData<List<Event>?>()
  val finishedEvents: LiveData<List<Event>?>
    get() = _finishedEvents

  private val _detail = MutableLiveData<Event?>()
  val detail: MutableLiveData<Event?>
    get() = _detail

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean>
    get() = _isLoading

  private val _error = MutableLiveData<String>()
  val error: LiveData<String>
    get() = _error

  fun loadActiveEvents() {
    _isLoading.postValue(true)
    eventRepository
      .getAllEvents(EventStatus.ACTIVE)
      .enqueue(
        object : Callback<EventApiResponse<List<Event>>> {
          override fun onResponse(
            call: Call<EventApiResponse<List<Event>>>,
            response: Response<EventApiResponse<List<Event>>>,
          ) {
            _isLoading.postValue(false)
            if (response.isSuccessful) {
              val events = response.body()?.listEvents
              _activeEvents.postValue(events)
              if (events.isNullOrEmpty()) {
                _error.postValue("No events found")
              }
            } else {
              _error.postValue(response.message())
            }

            _isLoading.postValue(false)
          }

          override fun onFailure(call: Call<EventApiResponse<List<Event>>>, t: Throwable) {
            _isLoading.postValue(false)
            _error.value = t.message
          }
        }
      )
  }

  fun loadFinishedEvents() {
    _isLoading.postValue(true)
    eventRepository
      .getAllEvents(EventStatus.FINISHED)
      .enqueue(
        object : Callback<EventApiResponse<List<Event>>> {
          override fun onResponse(
            call: Call<EventApiResponse<List<Event>>>,
            response: Response<EventApiResponse<List<Event>>>,
          ) {
            _isLoading.postValue(false)
            if (response.isSuccessful) {
              val events = response.body()?.listEvents
              _finishedEvents.postValue(events)
              if (events.isNullOrEmpty()) {
                _error.postValue("No events found")
              }
            } else {
              _error.postValue(response.message())
            }

            _isLoading.postValue(false)
          }

          override fun onFailure(call: Call<EventApiResponse<List<Event>>>, t: Throwable) {
            _isLoading.postValue(false)
            _error.value = t.message
          }
        }
      )
  }

  fun getEventDetail(eventId: Int) {
    _isLoading.postValue(true)
    eventRepository
      .getEventDetail(eventId)
      .enqueue(
        object : Callback<EventApiResponse<Event>> {
          override fun onResponse(
            call: Call<EventApiResponse<Event>>,
            response: Response<EventApiResponse<Event>>,
          ) {
            _isLoading.postValue(false)
            if (response.isSuccessful) {
              val event = response.body()?.event
              _detail.postValue(event)
              if (event == null) {
                _error.postValue("No event found")
              }
            } else {
              _error.postValue(response.message())
            }

            _isLoading.postValue(false)
          }

          override fun onFailure(call: Call<EventApiResponse<Event>>, t: Throwable) {
            _isLoading.postValue(false)
            _error.value = t.message
          }
        }
      )
  }

  fun searchActiveEvents(query: String) {
    _isLoading.postValue(true)
    eventRepository
      .searchEvents(EventStatus.ACTIVE, query)
      .enqueue(
        object : Callback<EventApiResponse<List<Event>>> {
          override fun onResponse(
            call: Call<EventApiResponse<List<Event>>>,
            response: Response<EventApiResponse<List<Event>>>,
          ) {
            _isLoading.postValue(false)
            if (response.isSuccessful) {
              val events = response.body()?.listEvents
              _activeEvents.postValue(events)
              if (events.isNullOrEmpty()) {
                _error.postValue("No events found")
              }
            } else {
              _error.postValue(response.message())
            }

            _isLoading.postValue(false)
          }

          override fun onFailure(call: Call<EventApiResponse<List<Event>>>, t: Throwable) {
            _isLoading.postValue(false)
            _error.value = t.message
          }
        }
      )
  }

  fun searchFinishedEvents(query: String) {
    _isLoading.postValue(true)
    eventRepository
      .searchEvents(EventStatus.FINISHED, query)
      .enqueue(
        object : Callback<EventApiResponse<List<Event>>> {
          override fun onResponse(
            call: Call<EventApiResponse<List<Event>>>,
            response: Response<EventApiResponse<List<Event>>>,
          ) {
            _isLoading.postValue(false)
            if (response.isSuccessful) {
              val events = response.body()?.listEvents
              _finishedEvents.postValue(events)
              if (events.isNullOrEmpty()) {
                _error.postValue("No events found")
              }
            } else {
              _error.postValue(response.message())
            }

            _isLoading.postValue(false)
          }

          override fun onFailure(call: Call<EventApiResponse<List<Event>>>, t: Throwable) {
            _isLoading.postValue(false)
            _error.value = t.message
          }
        }
      )
  }
}

package com.example.isenapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope


class EventViewModel : ViewModel() {

    private val repository = EventRepository()
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun fetchEvent() {
        viewModelScope.launch {
            val fetchedEvents = repository.getEvents()
            fetchedEvents?.let {
                _events.value = it
            }
        }
    }
    fun addEvent(event: Event) {
        _events.value = _events.value.orEmpty() + event
    }

}

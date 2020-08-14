package com.aodev.githubevents.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aodev.githubevents.network.data.EventsItem

private val TAG = "DetailViewModel"

class DetailViewModel(item: EventsItem) : ViewModel() {
    private val _event = MutableLiveData<EventsItem>()
    init {
        _event.value = item
    }

    fun GetImageUrl(): String? {
        return _event.value?.actor?.avatarUrl
    }

    fun GetId(): String? {
        return _event.value?.id
    }
}
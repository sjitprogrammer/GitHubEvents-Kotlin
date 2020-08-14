package com.aodev.githubevents.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aodev.githubevents.network.data.Events
import com.aodev.githubevents.network.data.EventsItem
import com.aodev.githubevents.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = "HomeViewModel"

class HomeViewModel : ViewModel() {
    val _eventsFetchAll = MutableLiveData<List<EventsItem>>()

    val isConnectFalse = MutableLiveData<Boolean>()
    val isSuccess = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        isConnectFalse.value =  false
        isSuccess.value = false
        isLoading.value = false
        fetchAllEvents()
    }

    fun fetchAllEvents() {
        _eventsFetchAll.value = ArrayList()
        isSuccess.value = false
        isLoading.value = false
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val allEvents = HomeRepository().fetchAllEvents()
                withContext(Dispatchers.Main) {
                    if (allEvents.isSuccessful) {
                        isConnectFalse.value =  false
                        isLoading.value = true
                        _eventsFetchAll.value = allEvents.body()
                    } else {
                        isLoading.value = true
                        isConnectFalse.value =  true
                    }
                }
            } catch (e: Exception) {
                isLoading.postValue(true)
                isConnectFalse.postValue(true)
                Log.e(TAG, "fetchAllEvents error : "+e.toString())
            }
        }
    }
}
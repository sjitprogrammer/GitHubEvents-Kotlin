package com.aodev.githubevents.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aodev.githubevents.network.data.EventsItem

class DetailViewModelFactory(private val pokemon: EventsItem) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(pokemon) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
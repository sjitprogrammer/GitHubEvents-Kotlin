package com.aodev.githubevents.screens.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aodev.githubevents.network.data.EventsItem
import java.text.SimpleDateFormat

private val TAG = "DetailViewModel"

class DetailViewModel(item: EventsItem) : ViewModel() {
    private val _event = MutableLiveData<EventsItem>()
    val _event_id = MutableLiveData<String>()
    val _display_name = MutableLiveData<String>()
    val _actor_id = MutableLiveData<String>()
    val _actor_login = MutableLiveData<String>()
    val _actor_url = MutableLiveData<String>()
    val _create_at = MutableLiveData<String>()
    val _repo_url = MutableLiveData<String>()
    val _repo_id = MutableLiveData<String>()
    val _repo_name = MutableLiveData<String>()
    val _type = MutableLiveData<String>()

    private val _eventGotoGithub = MutableLiveData<Boolean>()
    val eventGotoGithub: LiveData<Boolean>
        get() = _eventGotoGithub

    init {
        _event.value = item
        setAllData()
        setDataRepo()
    }



    private fun setAllData() {
        _event_id.value = _event.value?.id
        _display_name.value = _event.value?.actor?.displayLogin ?: ""
        _actor_id.value = _event.value?.actor?.id.toString() ?: ""
        _actor_login.value = _event.value?.actor?.login ?: ""
        _actor_url.value = _event.value?.actor?.url ?: ""
        _type.value=_event.value?.type
        val parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
        val output: String = formatter.format(parser.parse(_event.value?.createdAt ?: ""));
        _create_at.value = output
    }

    private fun setDataRepo() {
        _repo_id.value = _event.value?.repo?.id.toString() ?: ""
        _repo_name.value = _event.value?.repo?.name ?: ""
        _repo_url.value = _event.value?.repo?.url ?: ""
    }

    fun GetImageUrl(): String? {
        return _event.value?.actor?.avatarUrl
    }

    fun GetId(): String? {
        return _event.value?.id
    }

    fun getGithubRepoUrl(): String? {
        return _repo_url.value.toString()
    }

    fun onGotoGithubRepo() {
        _eventGotoGithub.value = true
    }

    fun onGotoGithubRepoComplete() {
        _eventGotoGithub.value = false
    }


}
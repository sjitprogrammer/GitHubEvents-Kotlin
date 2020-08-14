package com.aodev.githubevents.data


import com.google.gson.annotations.SerializedName

data class EventActor(
    val avatarUrl: String,
    val displayLogin: String,
    val gravatarId: String,
    val id: Int,
    val login: String,
    val url: String
)
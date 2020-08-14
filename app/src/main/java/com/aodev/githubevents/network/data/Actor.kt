package com.aodev.githubevents.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("display_login")
    val displayLogin: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String
):Parcelable
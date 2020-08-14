package com.aodev.githubevents.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EventsItem(
    @SerializedName("actor")
    val actor: Actor,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("org")
    val org: Org,
    @SerializedName("payload")
    val payload: Payload,
    @SerializedName("public")
    val `public`: Boolean,
    @SerializedName("repo")
    val repo: Repo,
    @SerializedName("type")
    val type: String
):Parcelable
package com.aodev.githubevents.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Commit(
    @SerializedName("author")
    val author: Author,
    @SerializedName("distinct")
    val distinct: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("sha")
    val sha: String,
    @SerializedName("url")
    val url: String
):Parcelable
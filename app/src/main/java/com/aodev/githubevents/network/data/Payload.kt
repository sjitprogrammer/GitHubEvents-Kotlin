package com.aodev.githubevents.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payload(
    @SerializedName("before")
    val before: String,
    @SerializedName("commits")
    val commits: List<Commit>,
    @SerializedName("distinct_size")
    val distinctSize: Int,
    @SerializedName("head")
    val head: String,
    @SerializedName("push_id")
    val pushId: Long,
    @SerializedName("ref")
    val ref: String,
    @SerializedName("size")
    val size: Int
):Parcelable
package com.aodev.githubevents.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Author(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
):Parcelable
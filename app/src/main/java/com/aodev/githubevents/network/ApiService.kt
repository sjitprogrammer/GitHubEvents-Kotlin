package com.aodev.githubevents.network

import com.aodev.githubevents.network.data.Events
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.github.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("events")
    suspend fun fetchAllEvents() : Response<Events>

}

object MyApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
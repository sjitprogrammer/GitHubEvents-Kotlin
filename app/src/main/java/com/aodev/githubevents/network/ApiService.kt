package com.aodev.githubevents.network

import com.aodev.githubevents.network.data.Events
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.github.com/"
private const val ACCESS_TOKEN = "?access_token=58677159cddc49b76540ab68ffe6897914abba8c"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ApiService {

    @GET("events"+ACCESS_TOKEN)
    suspend fun fetchAllEvents() : Response<Events>

}

object MyApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
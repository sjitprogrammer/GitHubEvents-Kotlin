package com.aodev.githubevents.repository

import com.aodev.githubevents.network.MyApi
import com.aodev.githubevents.network.data.Events
import retrofit2.Response

class HomeRepository {

    suspend fun fetchAllEvents(): Response<Events> {
        return MyApi.retrofitService.fetchAllEvents()
    }
}
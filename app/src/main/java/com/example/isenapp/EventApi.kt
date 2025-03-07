package com.example.isenapp

import retrofit2.http.GET
import retrofit2.Response


interface EventApi {
    @GET("events.json")
    suspend fun getEvents(): Response<List<Event>>
}
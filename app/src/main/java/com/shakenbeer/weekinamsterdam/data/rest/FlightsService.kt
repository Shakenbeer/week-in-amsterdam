package com.shakenbeer.weekinamsterdam.data.rest

import com.shakenbeer.weekinamsterdam.data.rest.model.FlightsResponse
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface FlightsService {

    @Headers("X-RapidAPI-Key: 292a2901b1mshda87f2ee452e004p19997cjsn9b576deb0d04")
    @FormUrlEncoded
    @POST
    fun createSession()

    @GET
    fun getFlights(): Call<FlightsResponse>
}
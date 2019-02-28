package com.shakenbeer.weekinamsterdam.data.rest

import com.shakenbeer.weekinamsterdam.data.rest.model.FlightsResponse
import com.shakenbeer.weekinamsterdam.inWeek
import com.shakenbeer.weekinamsterdam.tomorrow
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface FlightsService {

    @Headers("X-RapidAPI-Key: 292a2901b1mshda87f2ee452e004p19997cjsn9b576deb0d04")
    @FormUrlEncoded
    @POST("pricing/v1.0")
    fun createSession(
        @Field("country") country: String,
        @Field("currency") currency: String,
        @Field("locale") locale: String,
        @Field("originPlace") originPlace: String,
        @Field("destinationPlace") destinationPlace: String,
        @Field("cabinClass") cabinClass: String,
        @Field("outboundDate") outboundDate: String,
        @Field("inboundDate") inboundDate: String
    ): Call<Any>

    @Headers("X-RapidAPI-Key: 292a2901b1mshda87f2ee452e004p19997cjsn9b576deb0d04")
    @GET("pricing/uk2/v1.0/{sessionkey}")
    fun getFlights(
        @Path("sessionkey") sessionKey: String,
        @Query("pageIndex") pageIndex: Int = 0,
        @Query("pageSize") pageSize: Int = 50
    ): Call<FlightsResponse>
}
package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class FlightsResponse(

    @field:SerializedName("Status")
	val status: String? = null,

    @field:SerializedName("Carriers")
	val carriers: List<ApiCarrier?>? = null,

    @field:SerializedName("Legs")
	val legs: List<ApiLeg?>? = null,

    @field:SerializedName("Itineraries")
	val itineraries: List<ApiItinerary?>? = null,

    @field:SerializedName("Query")
	val query: ApiQuery? = null,

    @field:SerializedName("SessionKey")
	val sessionKey: String? = null,

    @field:SerializedName("Agents")
	val agents: List<ApiAgent?>? = null,

    @field:SerializedName("Segments")
	val segments: List<ApiSegment?>? = null,

    @field:SerializedName("Currencies")
	val currencies: List<ApiCurrency?>? = null,

    @field:SerializedName("Places")
	val places: List<ApiPlace?>? = null
)
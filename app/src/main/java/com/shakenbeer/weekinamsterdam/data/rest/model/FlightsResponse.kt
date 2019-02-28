package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class FlightsResponse(

    @SerializedName("Status")
	val status: String,

    @SerializedName("Carriers")
	val carriers: List<ApiCarrier>,

    @SerializedName("Legs")
	val legs: List<ApiLeg>,

    @SerializedName("Itineraries")
	val itineraries: List<ApiItinerary>,

    @SerializedName("Query")
	val query: ApiQuery,

    @SerializedName("SessionKey")
	val sessionKey: String,

    @SerializedName("Agents")
	val agents: List<ApiAgent>,

    @SerializedName("Segments")
	val segments: List<ApiSegment>,

    @SerializedName("Currencies")
	val currencies: List<ApiCurrency>,

    @SerializedName("Places")
	val places: List<ApiPlace>
)
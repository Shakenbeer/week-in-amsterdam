package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class FlightsResponse(

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("Carriers")
	val carriers: List<Carrier?>? = null,

	@field:SerializedName("Legs")
	val legs: List<Leg?>? = null,

	@field:SerializedName("Itineraries")
	val itineraries: List<Itinerary?>? = null,

	@field:SerializedName("Query")
	val query: Query? = null,

	@field:SerializedName("SessionKey")
	val sessionKey: String? = null,

	@field:SerializedName("Agents")
	val agents: List<Agent?>? = null,

	@field:SerializedName("Segments")
	val segments: List<Segment?>? = null,

	@field:SerializedName("Currencies")
	val currencies: List<CurrenciesItem?>? = null,

	@field:SerializedName("Places")
	val places: List<Place?>? = null
)
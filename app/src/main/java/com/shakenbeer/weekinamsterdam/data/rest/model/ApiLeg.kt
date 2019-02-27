package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiLeg(

    @field:SerializedName("SegmentIds")
	val segmentIds: List<Int?>? = null,

    @field:SerializedName("Duration")
	val duration: Int? = null,

    @field:SerializedName("Arrival")
	val arrival: String? = null,

    @field:SerializedName("Carriers")
	val carriers: List<Int?>? = null,

    @field:SerializedName("Directionality")
	val directionality: String? = null,

    @field:SerializedName("OriginStation")
	val originStation: Int? = null,

    @field:SerializedName("Departure")
	val departure: String? = null,

    @field:SerializedName("FlightNumbers")
	val flightNumbers: List<ApiFlightNumber?>? = null,

    @field:SerializedName("JourneyMode")
	val journeyMode: String? = null,

    @field:SerializedName("DestinationStation")
	val destinationStation: Int? = null,

    @field:SerializedName("Stops")
	val stops: List<Int?>? = null,

    @field:SerializedName("OperatingCarriers")
	val operatingCarriers: List<Int?>? = null,

    @field:SerializedName("Id")
	val id: String? = null
)
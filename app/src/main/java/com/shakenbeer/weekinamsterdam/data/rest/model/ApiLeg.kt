package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiLeg(

    @SerializedName("SegmentIds")
	val segmentIds: List<Int>,

    @SerializedName("Duration")
	val duration: Int,

    @SerializedName("Arrival")
	val arrival: String,

    @SerializedName("Carriers")
	val carriers: List<Int>,

    @SerializedName("Directionality")
	val directionality: String,

    @SerializedName("OriginStation")
	val originStation: Int,

    @SerializedName("Departure")
	val departure: String,

    @SerializedName("FlightNumbers")
	val flightNumbers: List<ApiFlightNumber>,

    @SerializedName("JourneyMode")
	val journeyMode: String,

    @SerializedName("DestinationStation")
	val destinationStation: Int,

    @SerializedName("Stops")
	val stops: List<Int>,

    @SerializedName("OperatingCarriers")
	val operatingCarriers: List<Int>,

    @SerializedName("Id")
	val id: String
)
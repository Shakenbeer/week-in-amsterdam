package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiSegment(

	@SerializedName("Directionality")
	val directionality: String,

	@SerializedName("OriginStation")
	val originStation: Int,

	@SerializedName("DepartureDateTime")
	val departureDateTime: String,

	@SerializedName("ArrivalDateTime")
	val arrivalDateTime: String,

	@SerializedName("JourneyMode")
	val journeyMode: String,

	@SerializedName("DestinationStation")
	val destinationStation: Int,

	@SerializedName("OperatingCarrier")
	val operatingCarrier: Int,

	@SerializedName("FlightNumber")
	val flightNumber: String,

	@SerializedName("Duration")
	val duration: Int,

	@SerializedName("Id")
	val id: Int,

	@SerializedName("Carrier")
	val carrier: Int
)
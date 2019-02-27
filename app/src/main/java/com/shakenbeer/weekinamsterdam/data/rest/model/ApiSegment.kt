package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiSegment(

	@field:SerializedName("Directionality")
	val directionality: String? = null,

	@field:SerializedName("OriginStation")
	val originStation: Int? = null,

	@field:SerializedName("DepartureDateTime")
	val departureDateTime: String? = null,

	@field:SerializedName("ArrivalDateTime")
	val arrivalDateTime: String? = null,

	@field:SerializedName("JourneyMode")
	val journeyMode: String? = null,

	@field:SerializedName("DestinationStation")
	val destinationStation: Int? = null,

	@field:SerializedName("OperatingCarrier")
	val operatingCarrier: Int? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null,

	@field:SerializedName("Duration")
	val duration: Int? = null,

	@field:SerializedName("Id")
	val id: Int? = null,

	@field:SerializedName("Carrier")
	val carrier: Int? = null
)
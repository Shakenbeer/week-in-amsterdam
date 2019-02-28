package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiFlightNumber(

	@SerializedName("CarrierId")
	val carrierId: Int,

	@SerializedName("FlightNumber")
	val flightNumber: String
)
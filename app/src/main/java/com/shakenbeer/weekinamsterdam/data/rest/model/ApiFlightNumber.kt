package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiFlightNumber(

	@field:SerializedName("CarrierId")
	val carrierId: Int? = null,

	@field:SerializedName("FlightNumber")
	val flightNumber: String? = null
)
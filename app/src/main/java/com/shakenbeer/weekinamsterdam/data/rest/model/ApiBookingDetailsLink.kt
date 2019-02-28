package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiBookingDetailsLink(

	@SerializedName("Method")
	val method: String,

	@SerializedName("Uri")
	val uri: String,

	@SerializedName("Body")
	val body: String
)
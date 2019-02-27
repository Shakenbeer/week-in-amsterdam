package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiBookingDetailsLink(

	@field:SerializedName("Method")
	val method: String? = null,

	@field:SerializedName("Uri")
	val uri: String? = null,

	@field:SerializedName("Body")
	val body: String? = null
)
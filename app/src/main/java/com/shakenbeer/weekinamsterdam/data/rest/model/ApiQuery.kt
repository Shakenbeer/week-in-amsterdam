package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiQuery(

	@SerializedName("Locale")
	val locale: String,

	@SerializedName("LocationSchema")
	val locationSchema: String,

	@SerializedName("Infants")
	val infants: Int,

	@SerializedName("InboundDate")
	val inboundDate: String,

	@SerializedName("OriginPlace")
	val originPlace: String,

	@SerializedName("CabinClass")
	val cabinClass: String,

	@SerializedName("Currency")
	val currency: String,

	@SerializedName("GroupPricing")
	val groupPricing: Boolean,

	@SerializedName("Country")
	val country: String,

	@SerializedName("Adults")
	val adults: Int,

	@SerializedName("Children")
	val children: Int,

	@SerializedName("OutboundDate")
	val outboundDate: String,

	@SerializedName("DestinationPlace")
	val destinationPlace: String
)
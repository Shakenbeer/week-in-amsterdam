package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class Query(

	@field:SerializedName("Locale")
	val locale: String? = null,

	@field:SerializedName("LocationSchema")
	val locationSchema: String? = null,

	@field:SerializedName("Infants")
	val infants: Int? = null,

	@field:SerializedName("InboundDate")
	val inboundDate: String? = null,

	@field:SerializedName("OriginPlace")
	val originPlace: String? = null,

	@field:SerializedName("CabinClass")
	val cabinClass: String? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("GroupPricing")
	val groupPricing: Boolean? = null,

	@field:SerializedName("Country")
	val country: String? = null,

	@field:SerializedName("Adults")
	val adults: Int? = null,

	@field:SerializedName("Children")
	val children: Int? = null,

	@field:SerializedName("OutboundDate")
	val outboundDate: String? = null,

	@field:SerializedName("DestinationPlace")
	val destinationPlace: String? = null
)
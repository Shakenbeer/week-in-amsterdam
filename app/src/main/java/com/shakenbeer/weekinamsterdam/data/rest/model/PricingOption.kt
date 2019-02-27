package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class PricingOption(

	@field:SerializedName("DeeplinkUrl")
	val deeplinkUrl: String? = null,

	@field:SerializedName("Price")
	val price: Double? = null,

	@field:SerializedName("Agents")
	val agents: List<Int?>? = null,

	@field:SerializedName("QuoteAgeInMinutes")
	val quoteAgeInMinutes: Int? = null
)
package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiPricingOption(

	@SerializedName("DeeplinkUrl")
	val deeplinkUrl: String,

	@SerializedName("Price")
	val price: Float,

	@SerializedName("Agents")
	val agents: List<Int>,

	@SerializedName("QuoteAgeInMinutes")
	val quoteAgeInMinutes: Int
)
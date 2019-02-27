package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

data class ApiItinerary(

    @field:SerializedName("InboundLegId")
	val inboundLegId: String? = null,

    @field:SerializedName("BookingDetailsLink")
	val bookingDetailsLink: ApiBookingDetailsLink? = null,

    @field:SerializedName("OutboundLegId")
	val outboundLegId: String? = null,

    @field:SerializedName("PricingOptions")
	val pricingOptions: List<ApiPricingOption?>? = null
)
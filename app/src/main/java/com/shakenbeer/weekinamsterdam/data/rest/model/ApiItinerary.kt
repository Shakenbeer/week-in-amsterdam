package com.shakenbeer.weekinamsterdam.data.rest.model

import com.google.gson.annotations.SerializedName

class ApiItinerary(

    @SerializedName("InboundLegId")
	val inboundLegId: String,

    @SerializedName("BookingDetailsLink")
	val bookingDetailsLink: ApiBookingDetailsLink,

    @SerializedName("OutboundLegId")
	val outboundLegId: String,

    @SerializedName("PricingOptions")
	val pricingOptions: List<ApiPricingOption>
)
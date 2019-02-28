package com.shakenbeer.weekinamsterdam.domain.model

class Query(
    val country: String = "DE",
    val currency: String = "EUR",
    val locale: String = "en",
    val originPlace: String = "BERL-sky",
    val destinationPlace: String = "AMST-sky",
    val cabinClass: String = "economy",
    val outboundDate: String,
    val inboundDate: String
)
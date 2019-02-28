package com.shakenbeer.weekinamsterdam.domain.model

class Leg(
        val id: Int,
        val airline: Airline,
        val flightNumber: Int,
        val departure: Long,
        val arrival: Long,
        val origin: Airport,
        val destination: Airport
) {
    val flyWith
        get() = airline.name

    val from
        get() = origin.name

    val to
        get() = destination.name
}
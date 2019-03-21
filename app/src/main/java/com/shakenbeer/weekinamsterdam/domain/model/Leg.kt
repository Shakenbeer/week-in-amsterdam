package com.shakenbeer.weekinamsterdam.domain.model

class Leg(
        val id: String,
        val duration: Int,
        val origin: Airport,
        val destination: Airport,
        val departure: String,
        val arrival: String,
        val stops: Int
)
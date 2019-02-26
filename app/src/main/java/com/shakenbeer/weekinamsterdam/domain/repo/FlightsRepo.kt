package com.shakenbeer.weekinamsterdam.domain.repo

import com.shakenbeer.weekinamsterdam.domain.model.Flight

interface FlightsRepo {
    fun getFlights(request: String): List<Flight>
}
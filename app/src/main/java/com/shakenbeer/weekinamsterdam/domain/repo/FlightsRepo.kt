package com.shakenbeer.weekinamsterdam.domain.repo

import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.model.Query

interface FlightsRepo {
    fun getTopFlights(request: Query): List<Flight>
}
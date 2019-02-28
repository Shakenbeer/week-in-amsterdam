package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.model.Query

interface FlightsSource {
    fun topFlights(request: Query): List<Flight>
}
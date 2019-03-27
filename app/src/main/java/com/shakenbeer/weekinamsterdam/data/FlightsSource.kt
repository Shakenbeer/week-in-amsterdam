package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query

interface FlightsSource {
    fun topFlights(request: Query): List<Itinerary>
}
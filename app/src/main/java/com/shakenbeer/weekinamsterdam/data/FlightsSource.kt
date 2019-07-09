package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query

interface FlightsSource {
    suspend fun topFlights(request: Query): List<Itinerary>
}
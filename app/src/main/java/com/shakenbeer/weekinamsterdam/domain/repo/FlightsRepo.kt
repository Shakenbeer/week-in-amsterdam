package com.shakenbeer.weekinamsterdam.domain.repo

import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query

interface FlightsRepo {
    suspend fun getTopFlights(request: Query): List<Itinerary>
}
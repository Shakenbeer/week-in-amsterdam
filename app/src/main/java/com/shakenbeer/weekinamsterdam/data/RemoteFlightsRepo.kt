package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo

class RemoteFlightsRepo(private val remoteFlightsSource: FlightsSource): FlightsRepo {

    override fun getTopFlights(request: Query): List<Itinerary> {
        return remoteFlightsSource.topFlights(request)
    }
}
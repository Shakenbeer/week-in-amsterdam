package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.injection.ApplicationModule.Companion.REMOTE_SOURCE
import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import javax.inject.Inject
import javax.inject.Named

class RemoteFlightsRepo @Inject constructor(
        @Named(REMOTE_SOURCE) private val remoteFlightsSource: FlightsSource): FlightsRepo {

    override fun getTopFlights(request: Query): List<Itinerary> {
        return remoteFlightsSource.topFlights(request)
    }
}
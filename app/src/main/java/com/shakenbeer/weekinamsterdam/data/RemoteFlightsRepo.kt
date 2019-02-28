package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.injection.ApplicationModule.Companion.REMOTE_SOURCE
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.model.Query
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import javax.inject.Inject
import javax.inject.Named

class RemoteFlightsRepo @Inject constructor(
        @Named(REMOTE_SOURCE) private val remoteFlightsSource: FlightsSource): FlightsRepo {

    override fun getTopFlights(request: Query): List<Flight> {
        return remoteFlightsSource.topFlights(request)
    }
}
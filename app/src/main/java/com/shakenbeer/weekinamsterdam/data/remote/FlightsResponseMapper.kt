package com.shakenbeer.weekinamsterdam.data.remote

import com.shakenbeer.weekinamsterdam.data.rest.model.ApiPlace
import com.shakenbeer.weekinamsterdam.data.rest.model.FlightsResponse
import com.shakenbeer.weekinamsterdam.domain.model.Airport
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.model.Leg
import com.shakenbeer.weekinamsterdam.domain.model.Supplier

object FlightsResponseMapper {

    private const val UNKNOWN_ID = -1
    private val unknownAirport = Airport(UNKNOWN_ID, "Unknown airport", "UNK", "Unknown city")
    private val unknownLeg = Leg(
        UNKNOWN_ID.toString(),
        0,
        unknownAirport,
        unknownAirport,
        "",
        "",
        0
    )
    private val unknownSupplier = Supplier(UNKNOWN_ID, "Unknown supplier")

    fun responseToFlights(flightsResponse: FlightsResponse): List<Flight> {

        val cityMap: Map<Int, ApiPlace> = flightsResponse.places
            .filter { it.type == "City" }
            .associate { Pair(it.id, it) }

        val airportMap: Map<Int, Airport> = flightsResponse.places.associate {
            Pair(
                it.id,
                if (it.type != "Airport") {
                    unknownAirport
                } else {
                    Airport(it.id, it.name, it.code, cityMap[it.parentId]?.name ?: "")
                }
            )
        }.filter { it.value != unknownAirport }

        val legMap: Map<String, Leg> = flightsResponse.legs.associate {
            Pair(
                it.id,
                Leg(
                    id = it.id,
                    duration = it.duration,
                    origin = airportMap[it.originStation] ?: unknownAirport,
                    destination = airportMap[it.destinationStation] ?: unknownAirport,
                    departure = it.departure,
                    arrival = it.arrival,
                    stops = it.stops.size
                )
            )
        }.filter { it.value.origin != unknownAirport && it.value.destination != unknownAirport }

        val agentMap: Map<Int, Supplier> = flightsResponse.agents.associate {
            Pair(it.id, Supplier(it.id, it.name))
        }

        return flightsResponse.itineraries.map { itinerary ->
            val cheapestOption = itinerary.pricingOptions.sortedBy { it.price }.first()
            Flight(
                id = "${itinerary.outboundLegId};${itinerary.inboundLegId}",
                departLeg = legMap[itinerary.outboundLegId] ?: unknownLeg,
                returnLeg = legMap[itinerary.inboundLegId] ?: unknownLeg,
                cost = cheapestOption.price,
                supplier = agentMap[cheapestOption.agents.first()] ?: unknownSupplier,
                ticketClass = flightsResponse.query.cabinClass
            )
        }.filter { it.departLeg != unknownLeg && it.returnLeg != unknownLeg && it.supplier != unknownSupplier }
    }
}
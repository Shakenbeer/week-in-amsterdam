package com.shakenbeer.weekinamsterdam.data.remote

import com.shakenbeer.weekinamsterdam.data.rest.model.FlightsResponse
import com.shakenbeer.weekinamsterdam.domain.model.*

object FlightsResponseMapper {

    private val UNKNOWN_ID = -1

    val unknownAirport = Airport(UNKNOWN_ID, "Unknown airport", "UNK")
    val unknownLeg = Leg(
        UNKNOWN_ID.toString(),
        0,
        unknownAirport,
        unknownAirport,
        "",
        "",
        0
    )
    val unknownSupplier = Supplier(UNKNOWN_ID, "Unknown supplier")
    fun responseToFlights(flightsResponse: FlightsResponse): List<Flight> {

        val airportMap: Map<Int, Airport> = flightsResponse.places.associate {
            Pair(it.id, if (it.type != "Airport") {
                unknownAirport
            } else {
               Airport(it.id, it.name, it.code)
            })
        }.filter { it.value != unknownAirport }

        val legMap: Map<String, Leg> = flightsResponse.legs.associate {
            Pair(it.id, Leg(it.id, it.duration, airportMap[it.originStation] ?: unknownAirport,
                airportMap[it.destinationStation] ?: unknownAirport, it.departure, it.arrival, it.stops.size))
        }.filter { it.value.origin != unknownAirport && it.value.destination != unknownAirport }

        val agentMap: Map<Int, Supplier> = flightsResponse.agents.associate {
            Pair(it.id, Supplier(it.id, it.name))
        }

        return flightsResponse.itineraries.map { itinerary ->
            val cheapestOption = itinerary.pricingOptions.sortedBy { it.price }.first()
            Flight(
                "${itinerary.outboundLegId};${itinerary.inboundLegId}",
                legMap[itinerary.outboundLegId] ?: unknownLeg,
                legMap[itinerary.inboundLegId] ?: unknownLeg,
                cheapestOption.price,
                agentMap[cheapestOption.agents.first()] ?: unknownSupplier,
                flightsResponse.query.cabinClass)
        }.filter { it.departLeg != unknownLeg && it.returnLeg != unknownLeg && it.supplier != unknownSupplier }
    }
}
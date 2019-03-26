package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.R
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.presentation.model.ViewFlight
import java.text.SimpleDateFormat
import java.util.*

object FlightMapper {

    private val skyscannerDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())

    private val dateFormatter = SimpleDateFormat("d MMM", Locale.getDefault())

    fun flightToView(flight: Flight): ViewFlight {
        return ViewFlight(flight.id).apply {
            with (flight.departLeg) {
                departSegment.originCode = origin.code
                departSegment.originCity = origin.city
                departSegment.startDate = dateFrom(departure)
                departSegment.startDay = dayFrom(departure)
                departSegment.startTime = timeFrom(departure)

                departSegment.destCode = destination.code
                departSegment.destCity = destination.city
                departSegment.finishDate = timeFrom(arrival)
                departSegment.finishDay = dayFrom(arrival)
                departSegment.finishTime = timeFrom(arrival)

                departSegment.stops = stops
                departSegment.duration = "${duration / 60}h ${duration % 60}m"
            }

            with (flight.returnLeg) {
                returnSegment.originCode = origin.code
                returnSegment.originCity = origin.city
                returnSegment.startDate = dateFrom(departure)
                returnSegment.startDay = dayFrom(departure)
                returnSegment.startTime = timeFrom(departure)

                returnSegment.destCode = destination.code
                returnSegment.destCity = destination.city
                returnSegment.finishDate = timeFrom(arrival)
                returnSegment.finishDay = dayFrom(arrival)
                returnSegment.finishTime = timeFrom(arrival)

                returnSegment.stops = stops
                returnSegment.duration = "${duration / 60}h ${duration % 60}m"
            }
        }
    }

    private fun timeFrom(date: String): String = timeFormatter.format(skyscannerDateFormat.parse(date))

    private fun dayFrom(date: String): String = dayFormatter.format(skyscannerDateFormat.parse(date))

    private fun dateFrom(date: String): String = dateFormatter.format(skyscannerDateFormat.parse(date))
}
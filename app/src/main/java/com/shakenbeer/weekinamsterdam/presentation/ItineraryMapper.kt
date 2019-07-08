package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.presentation.model.ItineraryView
import java.text.SimpleDateFormat
import java.util.*

object ItineraryMapper {

    private val skyscannerDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.US)

    private val dayFormatter = SimpleDateFormat("EEE", Locale.US)

    private val dateFormatter = SimpleDateFormat("d MMM", Locale.US)

    fun itineraryToView(itinerary: Itinerary): ItineraryView {
        return ItineraryView(itinerary.id).apply {
            supplier = itinerary.suppliedBy
            ticketClass = itinerary.ticketClass.toLowerCase().capitalize()
            cost = itinerary.cost

            with (itinerary.departLeg) {
                departLeg.originCode = origin.code
                departLeg.originCity = origin.city
                departLeg.startDate = dateFrom(departure)
                departLeg.startDay = dayFrom(departure)
                departLeg.startTime = timeFrom(departure)

                departLeg.destCode = destination.code
                departLeg.destCity = destination.city
                departLeg.finishDate = dateFrom(arrival)
                departLeg.finishDay = dayFrom(arrival)
                departLeg.finishTime = timeFrom(arrival)

                departLeg.stops = stops
                departLeg.duration = "${duration / 60}h ${duration % 60}m"
            }

            with (itinerary.returnLeg) {
                returnLeg.originCode = origin.code
                returnLeg.originCity = origin.city
                returnLeg.startDate = dateFrom(departure)
                returnLeg.startDay = dayFrom(departure)
                returnLeg.startTime = timeFrom(departure)

                returnLeg.destCode = destination.code
                returnLeg.destCity = destination.city
                returnLeg.finishDate = dateFrom(arrival)
                returnLeg.finishDay = dayFrom(arrival)
                returnLeg.finishTime = timeFrom(arrival)

                returnLeg.stops = stops
                returnLeg.duration = "${duration / 60}h ${duration % 60}m"
            }
        }
    }

    private fun timeFrom(date: String): String = timeFormatter.format(skyscannerDateFormat.parse(date))

    private fun dayFrom(date: String): String = dayFormatter.format(skyscannerDateFormat.parse(date))

    private fun dateFrom(date: String): String = dateFormatter.format(skyscannerDateFormat.parse(date))
}
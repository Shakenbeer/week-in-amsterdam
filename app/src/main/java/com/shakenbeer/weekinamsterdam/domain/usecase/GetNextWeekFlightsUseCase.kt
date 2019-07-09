package com.shakenbeer.weekinamsterdam.domain.usecase

import androidx.annotation.VisibleForTesting
import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import java.text.SimpleDateFormat
import java.util.*

class GetNextWeekFlightsUseCase(private val flightsRepo: FlightsRepo) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun execute(): List<Itinerary> {
        val query = buildRequest(Calendar.getInstance())
        return flightsRepo.getTopFlights(query)
    }

    @Suppress("MemberVisibilityCanBePrivate")
    @VisibleForTesting
    internal fun buildRequest(calendar: Calendar): Query {
        calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val departureDate = calendar.run {
            add(Calendar.DATE, 1)
            time.time
        }
        val returnDate = calendar.run {
            add(Calendar.DATE, 7)
            time.time
        }

        return Query(outboundDate = dateFormatter.format(departureDate), inboundDate = dateFormatter.format(returnDate))
    }
}
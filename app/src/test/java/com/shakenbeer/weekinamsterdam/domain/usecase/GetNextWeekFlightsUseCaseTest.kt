package com.shakenbeer.weekinamsterdam.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import com.shakenbeer.weekinamsterdam.Utils.errorFromFile
import com.shakenbeer.weekinamsterdam.data.remote.SkyscannerServerError
import com.shakenbeer.weekinamsterdam.data.remote.UnexpectedServerError
import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.repo.FlightsRepo
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class GetNextWeekFlightsUseCaseTest {

    @Mock
    private lateinit var flightsRepo: FlightsRepo

    private lateinit var getNextWeekFlightsUseCase: GetNextWeekFlightsUseCase

    @Before
    fun setUp() {
        getNextWeekFlightsUseCase = GetNextWeekFlightsUseCase(flightsRepo)
    }

    @Test(expected = SkyscannerServerError::class)
    fun `if repo throws momondo error then throw momondo error`() {
        val serverError = errorFromFile("error_response.json")
        val skyscannerServerError = SkyscannerServerError(serverError)
        doAnswer { throw skyscannerServerError }.whenever(flightsRepo).getTopFlights(any())

        getNextWeekFlightsUseCase.execute()
    }

    @Test(expected = UnexpectedServerError::class)
    fun `if repo throws unexpected error then throw unexpected error`() {
        doAnswer { throw UnexpectedServerError() }.whenever(flightsRepo).getTopFlights(any())
        getNextWeekFlightsUseCase.execute()
    }

    @Test
    fun `check flights list`() {
        val flights = listOf<Itinerary>()
        `when`(flightsRepo.getTopFlights(any())).thenReturn(flights)

        val result = getNextWeekFlightsUseCase.execute()
        assertEquals(flights, result)
    }

    @Test
    fun `check if request dates are from tomorrow and next week`() {
        val fixedDate = Date()

        val query = getNextWeekFlightsUseCase.buildRequest(
                Calendar.getInstance().apply { time = fixedDate }
        )

        val calendar = Calendar.getInstance().apply { time = fixedDate }
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

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

        assertEquals(departureDate, dateFormatter.parse(query.outboundDate).time)
        assertEquals(returnDate, dateFormatter.parse(query.inboundDate).time)
    }
}
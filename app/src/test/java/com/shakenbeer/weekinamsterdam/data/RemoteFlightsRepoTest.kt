package com.shakenbeer.weekinamsterdam.data

import com.nhaarman.mockitokotlin2.whenever
import com.shakenbeer.weekinamsterdam.Utils.errorFromFile
import com.shakenbeer.weekinamsterdam.data.remote.SkyscannerServerError
import com.shakenbeer.weekinamsterdam.data.remote.UnexpectedServerError
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.model.Query
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteFlightsRepoTest {

    @Mock
    private lateinit var flightsSource: FlightsSource

    private lateinit var remoteFlightsRepo: RemoteFlightsRepo

    @Before
    fun setUp() {
        remoteFlightsRepo = RemoteFlightsRepo(flightsSource)
    }

    @Test(expected = SkyscannerServerError::class)
    fun `if source throws scyscanner error then throw scyscanner error`() {
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        val serverError = errorFromFile("error_response.json")
        val scyscannerServerError = SkyscannerServerError(serverError)
        doAnswer { throw scyscannerServerError }.whenever(flightsSource).topFlights(request)

        remoteFlightsRepo.getTopFlights(request)
    }

    @Test(expected = UnexpectedServerError::class)
    fun `if source throws unexpected error then throw unexpected error`() {
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        doAnswer { throw UnexpectedServerError() }.whenever(flightsSource).topFlights(request)
        remoteFlightsRepo.getTopFlights(request)
    }

    @Test
    fun `check flights list` () {
        val flights = listOf<Flight>()
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        `when`(flightsSource.topFlights(request)).thenReturn(flights)

        val result = remoteFlightsRepo.getTopFlights(request)
        assertEquals(flights, result)
    }
}
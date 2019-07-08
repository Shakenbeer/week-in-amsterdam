package com.shakenbeer.weekinamsterdam.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import com.shakenbeer.weekinamsterdam.Connectivity
import com.shakenbeer.weekinamsterdam.Utils
import com.shakenbeer.weekinamsterdam.Utils.responseFromFile
import com.shakenbeer.weekinamsterdam.data.remote.FlightsResponseMapper.responseToFlights
import com.shakenbeer.weekinamsterdam.data.remote.SkyscannerServerError
import com.shakenbeer.weekinamsterdam.data.remote.UnexpectedServerError
import com.shakenbeer.weekinamsterdam.domain.usecase.GetNextWeekFlightsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FlightsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getNextWeekFlightsUseCase: GetNextWeekFlightsUseCase

    @Mock
    lateinit var connectivity: Connectivity

    private lateinit var flightsViewModel: FlightsViewModel

    @ExperimentalCoroutinesApi
    @Before
    @Throws(Exception::class)
    fun setUp() {
        flightsViewModel = FlightsViewModel(getNextWeekFlightsUseCase, connectivity)
        flightsViewModel.ioDispatcher = Dispatchers.Unconfined
    }

    //TODO this is integration test because of FlightsResponseMapper.responseToFlights
    @Test
    fun `check number of flights`() {
        val flights = responseToFlights(responseFromFile("proper_1st_page_results.json"))
        `when`(getNextWeekFlightsUseCase.execute()).thenReturn(flights)
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is DisplayState)
        assertEquals(flights.size, (flightsViewModel.flightsLiveData.value as DisplayState).itineraries.size)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `if no flights then show no flights`() {
        `when`(getNextWeekFlightsUseCase.execute()).thenReturn(emptyList())
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is NoFlightsState)
    }

    @Test
    fun `if no internet then show no internet` () {
        `when`(connectivity.isConnectedToInternet()).thenReturn(false)
        flightsViewModel.loadFlights()
        assert(flightsViewModel.flightsLiveData.value is NoInternetState)
    }

    @Test
    fun `if scyscanner server error then state is error`() {
        val serverError = Utils.errorFromFile("error_response.json")
        val scyscannerServerError = SkyscannerServerError(serverError)
        doAnswer { throw scyscannerServerError }.whenever(getNextWeekFlightsUseCase).execute()
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is ErrorState)
    }

    @Test
    fun `if unexpected server error then state is error`() {
        doAnswer { throw UnexpectedServerError() }.whenever(getNextWeekFlightsUseCase).execute()
        flightsViewModel.obtainFlights()
        assert(flightsViewModel.flightsLiveData.value is ErrorState)
    }

    //TODO this is integration test because of FlightsResponseMapper.responseToFlights
    @Test
    fun `verify conversion of a flight`() {
        val flights = responseToFlights(responseFromFile("proper_1st_page_results.json"))

        `when`(getNextWeekFlightsUseCase.execute()).thenReturn(flights)

        flightsViewModel.obtainFlights()

        assert(flightsViewModel.flightsLiveData.value is DisplayState)

        val displayState = flightsViewModel.flightsLiveData.value as DisplayState

        assertEquals(10, displayState.itineraries.size)

        val viewFlightSlice = displayState.itineraries.filter { viewFlight ->
            viewFlight.id == "13495-1905011635--32571,-32756-1-9451-1905012140;" +
                    "9451-1905080720--32756,-32695-1-13495-1905081215" }
        assertTrue(viewFlightSlice.size == 1)

        val viewFlight = viewFlightSlice.first()

        assertEquals("13495-1905011635--32571,-32756-1-9451-1905012140;" +
                "9451-1905080720--32756,-32695-1-13495-1905081215", viewFlight.id)

        assertEquals("Flighttix.de", viewFlight.supplier)
        assertEquals("Economy", viewFlight.ticketClass)
        assertEquals(251.65f, viewFlight.cost)

        assertEquals("LEJ", viewFlight.departLeg.originCode)
        assertEquals("AMS", viewFlight.departLeg.destCode)
        assertEquals("Leipzig", viewFlight.departLeg.originCity)
        assertEquals("Amsterdam", viewFlight.departLeg.destCity)
        assertEquals(1, viewFlight.departLeg.stops)
        assertEquals("5h 5m", viewFlight.departLeg.duration)
        assertEquals("1 May", viewFlight.departLeg.startDate)
        assertEquals("Wed", viewFlight.departLeg.startDay)
        assertEquals("16:35", viewFlight.departLeg.startTime)
        assertEquals("1 May", viewFlight.departLeg.finishDate)
        assertEquals("Wed", viewFlight.departLeg.finishDay)
        assertEquals("21:40", viewFlight.departLeg.finishTime)

        assertEquals("AMS", viewFlight.returnLeg.originCode)
        assertEquals("LEJ", viewFlight.returnLeg.destCode)
        assertEquals("Amsterdam", viewFlight.returnLeg.originCity)
        assertEquals("Leipzig", viewFlight.returnLeg.destCity)
        assertEquals(1, viewFlight.returnLeg.stops)
        assertEquals("4h 55m", viewFlight.returnLeg.duration)
        assertEquals("8 May", viewFlight.returnLeg.startDate)
        assertEquals("Wed", viewFlight.returnLeg.startDay)
        assertEquals("07:20", viewFlight.returnLeg.startTime)
        assertEquals("8 May", viewFlight.returnLeg.finishDate)
        assertEquals("Wed", viewFlight.returnLeg.finishDay)
        assertEquals("12:15", viewFlight.returnLeg.finishTime)
    }
}
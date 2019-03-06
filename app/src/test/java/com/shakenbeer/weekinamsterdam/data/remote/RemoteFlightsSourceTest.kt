package com.shakenbeer.weekinamsterdam.data.remote

import com.google.gson.GsonBuilder
import com.shakenbeer.weekinamsterdam.Utils.jsonStringFromFile
import com.shakenbeer.weekinamsterdam.Utils.responseFromFile
import com.shakenbeer.weekinamsterdam.data.rest.FlightsService
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import com.shakenbeer.weekinamsterdam.domain.model.Query
import okhttp3.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.Calls

@RunWith(MockitoJUnitRunner::class)
class RemoteFlightsSourceTest {

    @Mock
    private lateinit var flightsService: FlightsService

    private val errorConverter: Converter<ResponseBody, ServerError> = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl("http://example.com")
            .build()
            .responseBodyConverter(ServerError::class.java, emptyArray())

    private lateinit var remoteFlightsSource: RemoteFlightsSource

    @Before
    fun setUp() {
        remoteFlightsSource = RemoteFlightsSource(flightsService, errorConverter)
    }

    @Test(expected = SkyscannerServerError::class)
    fun `if bad request then throw scyscanner error`() {
        val error = jsonStringFromFile("error_response.json")
        val call = Calls.response(
                Response.error<String>(
                        400,
                        ResponseBody.create(MediaType.parse("application/json"), error)))
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        `when`(flightsService.createSession(request.country, request.currency, request.locale, request.originPlace,
            request.destinationPlace, request.cabinClass, request.outboundDate, request.inboundDate)).thenReturn(call)
        remoteFlightsSource.topFlights(request)
    }

    @Test(expected = UnexpectedServerError::class)
    fun `if server error then throw unexpected error`() {
        val call = Calls.response(Response.error<String>(500,
                ResponseBody.create(MediaType.parse("text/plain"), "Unexpected server error response")))
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        `when`(flightsService.createSession(request.country, request.currency, request.locale, request.originPlace,
            request.destinationPlace, request.cabinClass, request.outboundDate, request.inboundDate)).thenReturn(call)
        remoteFlightsSource.topFlights(request)
    }

    @Test
    fun `check success request mapping`() {
        val sessionKey = "02bd7f4a-eb42-4264-b5e0-854a7df97cf7"
        val sessionCall = Calls.response(Response.success("{}", okhttp3.Response.Builder() //
            .code(201)
            .request(Request.Builder().url("http://localhost/").build())
            .protocol(Protocol.HTTP_1_1)
            .message("Ok")
            .headers(Headers.of("Location", "http://partners.api.skyscanner.net/apiservices/pricing/uk1/v1.0/$sessionKey"))
            .build()))
        val request = Query(outboundDate = "2019-01-01", inboundDate = "2019-01-10")
        `when`(flightsService.createSession(request.country, request.currency, request.locale, request.originPlace,
            request.destinationPlace, request.cabinClass, request.outboundDate, request.inboundDate)).thenReturn(sessionCall)

        val flightsResponse = responseFromFile("proper_1st_page_results.json")
        val call = Calls.response(flightsResponse)
        `when`(flightsService.getFlights(sessionKey)).thenReturn(call)

        val result = remoteFlightsSource.topFlights(request)
        assertEquals(flightsResponse.itineraries.size, result.size)

        val secondFlight = result[1]
        assertEquals("13495-1905011050--31799-1-9451-1905011915;9451-1905081830--32571-1-13495-1905082310", secondFlight.id)
        assertEquals(253.11f, secondFlight.cost)
        assertEquals("Flighttix.de", secondFlight.supplier.name)

        val sixthFlightReturnLeg = result[5].returnLeg
        assertEquals("9451-1905081450--32695,-31781-1-13495-1905081755", sixthFlightReturnLeg.id)
        assertEquals("2019-05-08T14:50:00", sixthFlightReturnLeg.departure)
        assertEquals("2019-05-08T17:55:00", sixthFlightReturnLeg.arrival)
        assertEquals(185, sixthFlightReturnLeg.duration)
        assertEquals("AMS", sixthFlightReturnLeg.origin.code)
        assertEquals("Amsterdam", sixthFlightReturnLeg.origin.name)
        assertEquals("LEJ", sixthFlightReturnLeg.destination.code)
        assertEquals("Leipzig", sixthFlightReturnLeg.destination.name)
        assertEquals(1, sixthFlightReturnLeg.stops)
        
        val tenthFlightDepartLeg = result[9].departLeg
        assertEquals("13495-1905011440--32544-1-9451-1905011835", tenthFlightDepartLeg.id)
        assertEquals("2019-05-01T14:40:00", tenthFlightDepartLeg.departure)
        assertEquals("2019-05-01T18:35:00", tenthFlightDepartLeg.arrival)
        assertEquals(235, tenthFlightDepartLeg.duration)
        assertEquals("LEJ", tenthFlightDepartLeg.origin.code)
        assertEquals("Leipzig", tenthFlightDepartLeg.origin.name)
        assertEquals("AMS", tenthFlightDepartLeg.destination.code)
        assertEquals("Amsterdam", tenthFlightDepartLeg.destination.name)
        assertEquals(1, tenthFlightDepartLeg.stops)
    }
}

package com.shakenbeer.weekinamsterdam.data.remote

import com.shakenbeer.weekinamsterdam.data.FlightsSource
import com.shakenbeer.weekinamsterdam.data.remote.FlightsResponseMapper.responseToFlights
import com.shakenbeer.weekinamsterdam.data.rest.FlightsService
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.model.Query
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

class RemoteFlightsSource(
    private val flightsService: FlightsService,
    private val errorConverter: Converter<ResponseBody, ServerError>
) : FlightsSource {

    override suspend fun topFlights(request: Query): List<Itinerary> {
        val sessionCall = request.run {
            flightsService.createSession(
                country, currency, locale, originPlace, destinationPlace, cabinClass,
                outboundDate, inboundDate
            )
        }
        val sessionResponse = sessionCall.execute()
        if (sessionResponse.isSuccessful) {
            sessionResponse.headers().get("Location")?.let {
                val sessionKey = it.split('/').last()
                return flights(sessionKey, 0, 50)
            } ?: throw UnexpectedServerError()
        } else {
            sessionResponse.errorBody()?.let {
                try {
                    errorConverter.convert(it)
                        ?.let {serverError -> throw SkyscannerServerError(serverError)}
                } catch (e: IOException) {
                    throw UnexpectedServerError()
                }
            } ?: throw UnexpectedServerError()
        }
    }

    private suspend fun flights(sessionKey: String, pageIndex: Int, pageSize: Int): List<Itinerary> {
        val call = flightsService.getFlights(sessionKey, pageIndex, pageSize)
        val response = call.execute()
        if (response.isSuccessful) {
            return response.body()?.let { responseToFlights(it) }
                ?: throw UnexpectedServerError()
        } else {
            response.errorBody()?.let {
                try {
                    errorConverter.convert(it)
                        ?.let {serverError -> throw SkyscannerServerError(serverError)}
                } catch (e: IOException) {
                    throw UnexpectedServerError()
                }
            } ?: throw UnexpectedServerError()
        }
    }
}
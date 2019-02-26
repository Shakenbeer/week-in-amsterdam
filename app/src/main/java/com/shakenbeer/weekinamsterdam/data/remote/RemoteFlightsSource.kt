package com.shakenbeer.weekinamsterdam.data.remote

import com.shakenbeer.weekinamsterdam.data.FlightsSource
import com.shakenbeer.weekinamsterdam.data.remote.FlightsResponseMapper.responseToFlights
import com.shakenbeer.weekinamsterdam.data.rest.FlightsService
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException

class RemoteFlightsSource constructor(private val flightsService: FlightsService,
                                      private val errorConverter: Converter<ResponseBody, ServerError>) : FlightsSource {

    override fun flights(request: String): List<Flight> {
        val call = flightsService.getFlights()
        val response = call.execute()
        if (response.isSuccessful) {
            return response.body()?.let { responseToFlights(it) }
                    ?: throw UnexpectedServerError()
        } else {
            response.errorBody()?.let {
                try {
                    val serverError = errorConverter.convert(it)
                    throw SkyscannerServerError(serverError)
                } catch (e: IOException) {
                    throw UnexpectedServerError()
                }
            } ?: throw UnexpectedServerError()
        }
    }
}
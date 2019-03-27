package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.presentation.model.ViewFlight

sealed class FlightsViewState
object NoInternetState : FlightsViewState()
object LoadingState : FlightsViewState()
class ErrorState(val throwable: Throwable): FlightsViewState()
object NoFlightsState : FlightsViewState()
class DisplayState(val flights: List<ViewFlight>): FlightsViewState()
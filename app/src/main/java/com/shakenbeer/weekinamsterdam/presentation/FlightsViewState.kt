package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.presentation.model.ViewFlight

sealed class FlightsViewState
class NoInternetState: FlightsViewState()
class LoadingState: FlightsViewState()
class ErrorState(val throwable: Throwable): FlightsViewState()
class NoFlightsState: FlightsViewState()
class DisplayState(val flights: List<ViewFlight>): FlightsViewState()
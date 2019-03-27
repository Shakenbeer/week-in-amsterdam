package com.shakenbeer.weekinamsterdam.presentation

import com.shakenbeer.weekinamsterdam.presentation.model.ItineraryView

sealed class FlightsViewState
object NoInternetState : FlightsViewState()
object LoadingState : FlightsViewState()
class ErrorState(val throwable: Throwable): FlightsViewState()
object NoFlightsState : FlightsViewState()
class DisplayState(val itineraries: List<ItineraryView>): FlightsViewState()
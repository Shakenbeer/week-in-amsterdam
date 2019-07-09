package com.shakenbeer.weekinamsterdam.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shakenbeer.weekinamsterdam.Connectivity
import com.shakenbeer.weekinamsterdam.domain.usecase.GetNextWeekFlightsUseCase
import com.shakenbeer.weekinamsterdam.presentation.ItineraryMapper.itineraryToView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlightsViewModel(
    private val getNextWeekFlightsUseCase: GetNextWeekFlightsUseCase,
    private val connectivity: Connectivity
) : ViewModel() {

    val flightsLiveData = MutableLiveData<FlightsViewState>()

    //We need property for Dispatchers.IO to replace it in tests
    //until issue https://github.com/Kotlin/kotlinx.coroutines/issues/982 fixed
    var ioDispatcher = Dispatchers.IO

    init {
        loadFlights()
    }

    fun loadFlights() {
        if (connectivity.isConnectedToInternet()) {
            flightsLiveData.value = LoadingState
            obtainFlights()
        } else {
            flightsLiveData.value = NoInternetState
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    @VisibleForTesting
    internal fun obtainFlights() {
        viewModelScope.launch {
            try {
                getNextWeekFlightsUseCase.execute()
                    .map { itineraryToView(it) }.let {
                        if (it.isNotEmpty()) {
                            flightsLiveData.value = DisplayState(it)
                        } else {
                            flightsLiveData.value = NoFlightsState
                        }
                    }
            } catch (t: Throwable) {
                flightsLiveData.value = ErrorState(t)
            }
        }
    }
}
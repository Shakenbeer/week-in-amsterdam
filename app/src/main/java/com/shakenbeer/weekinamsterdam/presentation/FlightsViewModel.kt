package com.shakenbeer.weekinamsterdam.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shakenbeer.weekinamsterdam.Connectivity
import com.shakenbeer.weekinamsterdam.domain.model.Itinerary
import com.shakenbeer.weekinamsterdam.domain.usecase.GetNextWeekFlightsUseCase
import com.shakenbeer.weekinamsterdam.presentation.ItineraryMapper.flightToView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FlightsViewModel(
    private val getNextWeekFlightsUseCase: GetNextWeekFlightsUseCase,
    private val connectivity: Connectivity
) : ViewModel() {

    var flightsLiveData = MutableLiveData<FlightsViewState>()

    private var disposable: Disposable? = null

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
        disposable?.dispose()
        disposable = Observable.fromCallable { getNextWeekFlightsUseCase.execute() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable { list -> list }
            .map { itinerary: Itinerary -> flightToView(itinerary) }
            .toList()
            .subscribe({ flights ->
                if (flights.isNotEmpty()) {
                    flightsLiveData.value = DisplayState(flights)
                } else {
                    flightsLiveData.value = NoFlightsState
                }
            },
                { throwable -> flightsLiveData.value = ErrorState(throwable) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
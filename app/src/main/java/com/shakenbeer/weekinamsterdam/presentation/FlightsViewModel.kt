package com.shakenbeer.weekinamsterdam.presentation

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.shakenbeer.weekinamsterdam.WiaApplication
import com.shakenbeer.weekinamsterdam.domain.model.Flight
import com.shakenbeer.weekinamsterdam.domain.usecase.GetNextWeekFlightsUseCase
import com.shakenbeer.weekinamsterdam.isConnectedToInterned
import com.shakenbeer.weekinamsterdam.presentation.FlightMapper.flightToView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FlightsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var getNextWeekFlightsUseCase: GetNextWeekFlightsUseCase

    var flightsLiveData = MutableLiveData<FlightsViewState>()

    private var disposable: Disposable? = null

    init {
        (application as WiaApplication).component.inject(this)
        loadFlights()
    }

    fun loadFlights() {
        if (getApplication<WiaApplication>().isConnectedToInterned()) {
            flightsLiveData.value = LoadingState()
            obtainFlights()
        } else {
            flightsLiveData.value = NoInternetState()
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
            .map { flight: Flight -> flightToView(flight) }
            .toList()
            .subscribe({ flights ->
                if (flights.isNotEmpty()) {
                    flightsLiveData.value = DisplayState(flights)
                } else {
                    flightsLiveData.value = NoFlightsState()
                }
            },
                { throwable -> flightsLiveData.value = ErrorState(throwable) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}
package com.shakenbeer.weekinamsterdam.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakenbeer.weekinamsterdam.R
import com.shakenbeer.weekinamsterdam.hide
import com.shakenbeer.weekinamsterdam.presentation.model.ViewFlight
import com.shakenbeer.weekinamsterdam.show
import kotlinx.android.synthetic.main.activity_flights.*

class FlightsActivity : AppCompatActivity() {

    private val adapter = FlightAdapter()
    private lateinit var flightsViewModel: FlightsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        flightsRecyclerView.layoutManager = LinearLayoutManager(this)
        flightsRecyclerView.adapter = adapter

        flightsViewModel = ViewModelProviders.of(this).get(FlightsViewModel::class.java)

        flightsViewModel.flightsLiveData.observe(this, Observer { state ->
            swipeRefreshLayout.isRefreshing = false
            state?.let {
                when (state) {
                    is NoInternetState -> showNoInternet()
                    is LoadingState -> showLoading()
                    is ErrorState -> showError(state.throwable)
                    is NoFlightsState -> showNoFlights()
                    is DisplayState -> showFlights(state.flights)
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener { flightsViewModel.loadFlights() }

        retryButton.setOnClickListener { flightsViewModel.loadFlights() }
    }

    private fun showNoInternet() {
        Log.d("FlightsActivity", "No internet")
        flightsRecyclerView.hide()
        loading.hide()
        trouble.show()
        troubleImageView.setImageResource(R.drawable.wifi_off)
        troubleTextView.setText(R.string.no_internet_connection)
    }

    private fun showLoading() {
        Log.d("FlightsActivity", "Loading")
        flightsRecyclerView.hide()
        loading.show()
        trouble.hide()
    }

    private fun showError(throwable: Throwable) {
        Log.d("FlightsActivity", "Error")
        flightsRecyclerView.hide()
        loading.hide()
        trouble.show()
        troubleImageView.setImageResource(R.drawable.alert_circle_outline)
        troubleTextView.setText(R.string.server_error)
    }

    private fun showNoFlights() {
        Log.d("FlightsActivity", "No flights")
        flightsRecyclerView.hide()
        loading.hide()
        trouble.show()
        troubleImageView.setImageResource(R.drawable.airplane_off)
        troubleTextView.setText(R.string.no_flights_found)
    }

    private fun showFlights(flights: List<ViewFlight>) {
        Log.d("FlightsActivity", "flights ${flights.size}")
        flightsRecyclerView.show()
        loading.hide()
        trouble.hide()
        adapter.items = flights.toMutableList()
    }
}

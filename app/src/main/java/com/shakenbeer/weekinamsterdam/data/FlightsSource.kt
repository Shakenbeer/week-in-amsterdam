package com.shakenbeer.weekinamsterdam.data

import com.shakenbeer.weekinamsterdam.domain.model.Flight

interface FlightsSource {
    fun flights(request: String): List<Flight>
}
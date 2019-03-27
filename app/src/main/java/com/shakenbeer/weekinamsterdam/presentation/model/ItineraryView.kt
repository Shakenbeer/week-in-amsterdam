package com.shakenbeer.weekinamsterdam.presentation.model

class ItineraryView(val id: String,
                    val departLeg: LegView = LegView(),
                    val returnLeg: LegView = LegView()) {
    var supplier: String = ""
    var cost: Float = 0f
    var ticketClass = ""
}


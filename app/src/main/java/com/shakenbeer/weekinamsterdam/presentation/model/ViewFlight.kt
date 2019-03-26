package com.shakenbeer.weekinamsterdam.presentation.model

class ViewFlight(val id: String,
                 val departSegment: ViewSegment = ViewSegment(),
                 val returnSegment: ViewSegment = ViewSegment()) {
    var supplier: String = ""
    var cost: Float = 0f
    var ticketClass = ""
}


package com.shakenbeer.weekinamsterdam.domain.model

class Flight(
        val id: Int,
        val departLegs: List<Leg>,
        val returnLegs: List<Leg>,
        val departDuration: Int,
        val returnDuration: Int,
        val cost: Float,
        val supplier: Supplier,
        val ticketClass: TicketClass) {

    val suppliedBy
        get() = supplier.name
}
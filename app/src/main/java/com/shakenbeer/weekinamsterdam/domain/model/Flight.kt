package com.shakenbeer.weekinamsterdam.domain.model

class Flight(
        val id: String,
        val departLeg: Leg,
        val returnLeg: Leg,
        val cost: Float,
        val supplier: Supplier,
        val ticketClass: String) {

    val suppliedBy
        get() = supplier.name
}
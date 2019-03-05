package com.shakenbeer.weekinamsterdam.domain.model

class Leg(
        val id: String,
        val duration: Int,
        val origin: Airport,
        val destination: Airport,
        val departure: String,
        val arrival: String,
        val stops: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Leg

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
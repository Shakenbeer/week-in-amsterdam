package com.shakenbeer.weekinamsterdam.domain.model

class Airport(val id: Int, val name: String, val code: String, val city: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Airport

        if (id != other.id) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + code.hashCode()
        return result
    }
}
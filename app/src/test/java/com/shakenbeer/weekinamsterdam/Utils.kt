package com.shakenbeer.weekinamsterdam

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.shakenbeer.weekinamsterdam.data.rest.model.FlightsResponse
import com.shakenbeer.weekinamsterdam.data.rest.model.ServerError
import java.io.File

object Utils {

    private val gson: Gson = GsonBuilder()
            .create()

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @JvmStatic
    fun jsonStringFromFile(path: String): String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    @JvmStatic
    fun responseFromFile(path: String): FlightsResponse {
        val asString = jsonStringFromFile(path)
        val type = object : TypeToken<FlightsResponse>() {}.type
        return gson.fromJson<FlightsResponse>(asString, type)
    }

    @JvmStatic
    fun errorFromFile(path: String): ServerError {
        val asString = jsonStringFromFile(path)
        val type = object : TypeToken<ServerError>() {}.type
        return gson.fromJson<ServerError>(asString, type)
    }
}

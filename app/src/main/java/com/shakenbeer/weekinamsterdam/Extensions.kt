package com.shakenbeer.weekinamsterdam

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import java.text.SimpleDateFormat
import java.util.*

fun View.hide() {
    this.visibility = GONE
}

fun View.show() {
    this.visibility = VISIBLE
}

//TODO rid of deprecation
fun Context.isConnectedToInterned(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting ?: false
}

private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun Calendar.tomorrow(): String {
    dateOnly()
    add(Calendar.DATE, 1)
    return dateFormatter.format(time)
}

fun Calendar.inWeek(): String {
    dateOnly()
    add(Calendar.DATE, 8)
    return dateFormatter.format(time)
}

private fun Calendar.dateOnly() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}
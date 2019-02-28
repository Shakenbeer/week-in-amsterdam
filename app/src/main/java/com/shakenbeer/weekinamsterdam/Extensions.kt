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
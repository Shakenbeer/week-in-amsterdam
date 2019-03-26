package com.shakenbeer.weekinamsterdam

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateTest {

    private val skyscannerDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    private val dayFormatter = SimpleDateFormat("EEE", Locale.getDefault())

    private val dateFormatter = SimpleDateFormat("d MMM", Locale.getDefault())

    @Test
    fun checkTimeParsing() {
        val result = timeFormatter.format(skyscannerDateFormat.parse("2019-05-01T16:35:00"))
        assertEquals("16:35", result)
    }
}
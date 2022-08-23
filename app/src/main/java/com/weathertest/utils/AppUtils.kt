package com.weathertest.utils

import android.text.format.DateFormat
import java.util.*

object AppUtils {

    fun convertTimestampToDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd-MM HH:mm", calendar).toString()
    }
}
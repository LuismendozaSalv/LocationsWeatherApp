package com.mendosal.locationsweather.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Luis Mendoza on 8/30/2021.
 */
class DateUtils {

    companion object {
        fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            formatter.timeZone = timeZone
            return formatter.format(this)
        }
    }
}
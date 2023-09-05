package org.algotn.api.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        fun dateToLong(dateStr: String): Long {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val parisTimeZone = TimeZone.getTimeZone("Europe/Paris")
            sdf.timeZone = parisTimeZone

            val date = sdf.parse(dateStr)
            return date.time
        }
    }
}
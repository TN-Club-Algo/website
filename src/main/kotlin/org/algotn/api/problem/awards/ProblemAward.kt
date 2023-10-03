package org.algotn.api.problem.awards

import java.text.SimpleDateFormat
import java.util.*

abstract class ProblemAward(val problemSlug: String, val date: Long) {

    abstract fun isValid(): Boolean

    abstract fun clone(): ProblemAward

    abstract fun display(): String

    fun getDateFormatted(): String {
        val parisTimeZone = TimeZone.getTimeZone("Europe/Paris")
        val sdf2 = SimpleDateFormat("dd MMMM à HH:mm", Locale("fr", "FR"))
        sdf2.timeZone = parisTimeZone
        return sdf2.format(date)
    }
}
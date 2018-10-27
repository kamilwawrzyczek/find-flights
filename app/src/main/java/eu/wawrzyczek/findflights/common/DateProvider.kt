package eu.wawrzyczek.findflights.common

import java.util.*

class DateProvider {
    fun getCurrentDate() : SimpleDate {
        val calendar : Calendar = Calendar.getInstance()
        return SimpleDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    }
}
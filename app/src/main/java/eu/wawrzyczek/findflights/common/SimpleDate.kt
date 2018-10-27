package eu.wawrzyczek.findflights.common

import java.text.DateFormat
import java.util.*

data class SimpleDate(val year: Int, val month: Int, val dayOfMonth: Int) {
    val date: Date = GregorianCalendar(year, month, dayOfMonth).time
    val formattedString: String = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date)
}

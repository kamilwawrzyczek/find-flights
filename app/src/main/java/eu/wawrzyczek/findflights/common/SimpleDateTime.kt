package eu.wawrzyczek.findflights.common

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
data class SimpleDateTime(val year: Int, val month: Int, val dayOfMonth: Int, val hour : Int, val minute: Int) : Parcelable {
    @IgnoredOnParcel
    val date: Date = GregorianCalendar(year, month-1, dayOfMonth, hour, minute).time
    @IgnoredOnParcel
    val formattedString: String = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault()).format(date)
}

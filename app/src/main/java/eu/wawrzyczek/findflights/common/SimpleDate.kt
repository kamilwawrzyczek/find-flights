package eu.wawrzyczek.findflights.common

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
data class SimpleDate(val year: Int, val month: Int, val dayOfMonth: Int) : Parcelable {
    @IgnoredOnParcel
    val date: Date = GregorianCalendar(year, month, dayOfMonth).time
    @IgnoredOnParcel
    val formattedString: String = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault()).format(date)
}

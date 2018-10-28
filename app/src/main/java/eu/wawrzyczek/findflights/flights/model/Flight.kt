package eu.wawrzyczek.findflights.flights.model

import android.os.Parcelable
import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.search.model.Station
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat
import java.util.*

@Parcelize
data class Flight(
    val date: SimpleDateTime,
    val flightNumber: String,
    val duration: String,
    val price: Map<FareType, Price>,
    val currency: Currency,
    val origin: Station,
    val destination: Station,
    val infantsLeft: Int,
    val fareClass: String
) : Parcelable {
    @IgnoredOnParcel
    val fullPrice = price.values.map { it.amount }
        .reduce { a, b -> a + b }

    @IgnoredOnParcel
    val priceFormatted: String by lazy {
        val format = DecimalFormat("0.00")
        format.format(fullPrice) + currency.symbol
    }
}

package eu.wawrzyczek.findflights.flights.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

@Parcelize
data class Price(val amount: BigDecimal, val discountPercent: Int, val currency: Currency) : Parcelable {
    @IgnoredOnParcel
    val formatted: String by lazy {
        val format = DecimalFormat("0.00")
        format.format(amount) + currency.symbol
    }
}

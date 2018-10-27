package eu.wawrzyczek.findflights.search.model

import android.os.Parcelable
import eu.wawrzyczek.findflights.common.SimpleDate
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SearchData(
    val origin: Station, val destination: Station, val departureDate: SimpleDate,
    val adults: Int, val teens: Int, val children: Int
) : Parcelable
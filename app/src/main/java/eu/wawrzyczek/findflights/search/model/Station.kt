package eu.wawrzyczek.findflights.search.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Station(val code: String = "", val name: String = "", val alternateName:String? = null) : Parcelable {
    @IgnoredOnParcel
    val valid : Boolean
        get() = code.isNotEmpty() && name.isNotEmpty()

    override fun toString(): String = name
}

package eu.wawrzyczek.findflights.search

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station

class FlightSearchViewModel(dateProvider: DateProvider) : ViewModel() { //todo test
    val origin: ObservableField<Station> = ObservableField(Station())   //todo show error when not valid
    val destination: ObservableField<Station> = ObservableField(Station())

    val adults: ObservableInt = ObservableInt(1)
    val teens: ObservableInt = ObservableInt(0)
    val children: ObservableInt = ObservableInt(0)

    var departureDate: ObservableField<SimpleDate> = ObservableField(dateProvider.getCurrentDate())
    val valid: ObservableBoolean = object : ObservableBoolean(origin, destination, adults, teens, children) {
        override fun get(): Boolean = isDataValid()
    }

    fun search() {
        if (!valid.get()) return

        // origin.get(), destination.get() and departureDate.get() should never be null due to kotlin constraints
        val data = SearchData(
            origin.get()!!, destination.get()!!, departureDate.get()!!,
            adults.get(), teens.get(), children.get()
        )
        //todo open activity with results
    }

    private fun isDataValid(): Boolean {
        return origin.get()!!.valid && destination.get()!!.valid && isTicketCountValid()
    }

    private fun isTicketCountValid() = adults.get() + teens.get() + children.get() > 0
}
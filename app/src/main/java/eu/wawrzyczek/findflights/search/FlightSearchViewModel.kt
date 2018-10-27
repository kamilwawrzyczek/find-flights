package eu.wawrzyczek.findflights.search

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.common.toAsync
import eu.wawrzyczek.findflights.search.autocomplete.StationsRepository
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single

class FlightSearchViewModel(
    dateProvider: DateProvider,
    private val stationsRepository: StationsRepository,
    private val appNavigator: AppNavigator
) : ViewModel() {
    var stationsMap: Map<String, Station> = emptyMap()

    //todo show error when not valid
    val origin: ObservableField<Station> = object : ObservableField<Station>(Station()) {
        override fun set(value: Station) {
            super.set(stationsMap.getOrElse(value.name) { value })
        }
    }
    val destination: ObservableField<Station> = object : ObservableField<Station>(Station()) {
        override fun set(value: Station) {
            super.set(stationsMap.getOrElse(value.name) { value })
        }
    }

    val adults: ObservableInt = ObservableInt(1)
    val teens: ObservableInt = ObservableInt(0)
    val children: ObservableInt = ObservableInt(0)

    var departureDate: ObservableField<SimpleDate> = ObservableField(dateProvider.getCurrentDate())
    val valid: ObservableBoolean = object : ObservableBoolean(origin, destination, adults, teens, children) {
        override fun get(): Boolean = isDataValid()
    }

    val stations: Single<List<Station>>
        get() = stationsRepository.getStations()
            .doOnSuccess {
                stationsMap = it.associate { station -> station.name to station }
                origin.set(origin.get())
                destination.set(destination.get())
            }
            .toAsync()

    fun search() {
        if (!valid.get()) return

        // origin.get(), destination.get() and departureDate.get() should never be null due to kotlin constraints
        val data = SearchData(
            origin.get()!!, destination.get()!!, departureDate.get()!!,
            adults.get(), teens.get(), children.get()
        )
        appNavigator.navigateToFlightActivity(data)
    }

    private fun isDataValid(): Boolean {
        return origin.get()!!.valid && destination.get()!!.valid && isTicketCountValid()
    }

    private fun isTicketCountValid() = adults.get() + teens.get() + children.get() > 0
}
package eu.wawrzyczek.findflights.flights

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.common.toAsync
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

class FlightsViewModel(val searchData: SearchData) : ViewModel() {
    private var flightList = emptyList<Flight>()

    val searching = ObservableBoolean()
    val error = object : ObservableBoolean(searching) {
        override fun get(): Boolean {
            return super.get() && !searching.get()
        }
    }
    val empty = object : ObservableBoolean(searching, error) {
        override fun get(): Boolean {
            return flightList.isEmpty() && !searching.get() && !error.get()
        }
    }

    val flights: Single<List<Flight>>
        get() = Single.just(flightList)
            .filter { it.isNotEmpty() }
            .switchIfEmpty(getFlightsFromRepository())

    private fun getFlightsFromRepository(): Single<List<Flight>> {
        return Single.defer { // todo make api call
            Single.just(
                listOf<Flight>(
                    Flight(
                        SimpleDateTime(2018, 5, 1, 12, 35),
                        "FR 123", "12:33", "12.35", Currency.getInstance("EUR"),
                        Station(), Station(), 0, "", 0
                    )
                )
            ).delay(5, TimeUnit.SECONDS)
        }
            .doOnSubscribe { searching.set(true) }
            .doOnSuccess {
                flightList = it
                error.set(false)
            }
            .doOnError { error.set(true) }
            .doFinally { searching.set(false) }
            .onErrorReturn { emptyList() }
            .toAsync()
    }

    fun flightClick(flight: Flight) {
        // todo implement
    }

}

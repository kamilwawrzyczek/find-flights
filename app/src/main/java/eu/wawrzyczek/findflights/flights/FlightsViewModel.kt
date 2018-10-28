package eu.wawrzyczek.findflights.flights

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.plusAssign
import eu.wawrzyczek.findflights.common.toAsync
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.flights.search.SearchRepository
import eu.wawrzyczek.findflights.search.model.SearchData
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

private const val DEFAULT_PRICE_FILTER = 150

class FlightsViewModel(
    val searchData: SearchData, private val searchRepository: SearchRepository,
    private val navigator: AppNavigator
) : ViewModel() {

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

    val priceFilter = object: ObservableInt(DEFAULT_PRICE_FILTER) {
        override fun set(value: Int) {
            super.set(value)
            applyFilter(value)
        }
    }

    private var flightList = emptyList<Flight>()
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val currentFlights = BehaviorSubject.create<List<Flight>>()
    val flights: Observable<List<Flight>> = currentFlights

    fun loadFlights() {
        disposables += Single.just(flightList)
            .filter { it.isNotEmpty() }
            .switchIfEmpty(getFlightsFromRepository())
            .subscribe { _ -> applyFilter(priceFilter.get()) }
    }

    fun flightClick(flight: Flight) {
        navigator.navigateToFlightDetailsActivity(flight)
    }

    private fun applyFilter(priceFilter : Int) {
        currentFlights.onNext(flightList.filter { flight -> flight.price.values.none { it.amount > priceFilter.toBigDecimal() } })
    }

    private fun getFlightsFromRepository(): Single<List<Flight>> {
        return Single.defer { searchRepository.getFlights(searchData) }
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

    override fun onCleared() {
        disposables.clear()
    }
}

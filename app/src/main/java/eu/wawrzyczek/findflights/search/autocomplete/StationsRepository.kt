package eu.wawrzyczek.findflights.search.autocomplete

import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class StationsRepository(private val client: StationsClient) {
    private var cachedValues : Maybe<List<Station>>  = Maybe.empty()

    fun getStations() : Single<List<Station>> = cachedValues
        .switchIfEmpty(getStationsFromWeb())

    private fun getStationsFromWeb(): Single<List<Station>> = Single.defer { client.getStations() }
        .flatMapObservable { Observable.fromIterable(it.stations) }
        .map { Station(it.code, it.name, it.alternateName) }
        .toList()
        .doOnSuccess { cachedValues = Maybe.just(it) }
}
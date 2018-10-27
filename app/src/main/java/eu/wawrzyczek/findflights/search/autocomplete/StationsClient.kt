package eu.wawrzyczek.findflights.search.autocomplete

import eu.wawrzyczek.findflights.search.autocomplete.dto.StationsDto
import io.reactivex.Single
import retrofit2.http.GET

interface StationsClient {
    @GET("stations.json")
    fun getStations(): Single<StationsDto>
}

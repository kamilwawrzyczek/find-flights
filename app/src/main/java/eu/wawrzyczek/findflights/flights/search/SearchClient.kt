package eu.wawrzyczek.findflights.flights.search

import eu.wawrzyczek.findflights.flights.search.dto.FlightsDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val DEFAULT_FLEX_DAYS = 3

interface SearchClient {
    @GET("Availability?ToUs=AGREED")
    fun getFlights(@Query("origin") origin:String, @Query("destination")  destination: String,
                   @Query("dateout") date:String, @Query("adt") adt:Int, @Query("teen") teen : Int,
                   @Query("chd") chd:Int, @Query("roundtrip") roundTrip: Boolean = false,
                   @Query("flexdaysbeforeout") flexDaysBeforeOut: Int = DEFAULT_FLEX_DAYS,
                   @Query("flexdaysout") flexDaysOut: Int = DEFAULT_FLEX_DAYS,
                   @Query("flexdaysbeforein") flexDaysBeforeIn: Int = DEFAULT_FLEX_DAYS,
                   @Query("flexdaysin") flexDaysIn: Int = DEFAULT_FLEX_DAYS): Single<FlightsDto>
}
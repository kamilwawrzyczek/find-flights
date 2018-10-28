package eu.wawrzyczek.findflights.common

import android.content.Context
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.search.model.SearchData

class AppNavigator(private val context: Context) {
    fun navigateToFlightActivity(searchData: SearchData) {
        eu.wawrzyczek.findflights.flights.startActivity(context, searchData)
    }

    fun navigateToFlightDetailsActivity(flight: Flight) {
        eu.wawrzyczek.findflights.flights.details.startActivity(context, flight)
    }
}

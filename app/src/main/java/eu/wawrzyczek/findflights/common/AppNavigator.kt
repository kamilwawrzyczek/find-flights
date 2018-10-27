package eu.wawrzyczek.findflights.common

import android.content.Context
import eu.wawrzyczek.findflights.flights.startActivity
import eu.wawrzyczek.findflights.search.model.SearchData

class AppNavigator(private val context: Context) {
    fun navigateToFlightActivity(searchData: SearchData) {
        startActivity(context, searchData)
    }
}

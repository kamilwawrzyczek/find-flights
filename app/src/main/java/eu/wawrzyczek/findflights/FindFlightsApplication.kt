package eu.wawrzyczek.findflights

import android.app.Application
import eu.wawrzyczek.findflights.flights.flightsModule
import eu.wawrzyczek.findflights.search.flightSearchModule
import org.koin.android.ext.android.startKoin

class FindFlightsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule, apiModule, flightSearchModule, flightsModule))
    }
}

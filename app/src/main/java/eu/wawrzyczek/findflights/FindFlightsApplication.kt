package eu.wawrzyczek.findflights

import android.app.Application
import org.koin.android.ext.android.startKoin

class FindFlightsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(applicationModule))
    }
}

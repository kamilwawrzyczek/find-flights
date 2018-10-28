package eu.wawrzyczek.findflights.flights

import eu.wawrzyczek.findflights.flights.search.SearchRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val flightsModule = module {
    single { SearchRepository(get()) }
    viewModel { params -> FlightsViewModel(params[0], get(), get()) }
}
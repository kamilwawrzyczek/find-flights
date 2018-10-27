package eu.wawrzyczek.findflights.search

import eu.wawrzyczek.findflights.search.autocomplete.StationsRepository
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val flightSearchModule = module {
    viewModel { FlightSearchViewModel(get(), get(), get()) }
    single { StationMatchingStrategy() }
    single { StationsRepository(get()) }
}
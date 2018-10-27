package eu.wawrzyczek.findflights.search

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val flightSearchModule = module {
    viewModel { FlightSearchViewModel(get()) }
}
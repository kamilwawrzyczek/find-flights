package eu.wawrzyczek.findflights.flights

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val flightsModule = module {
    viewModel { params -> FlightsViewModel(params[0]) }
}
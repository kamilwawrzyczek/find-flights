package eu.wawrzyczek.findflights.flights.search.dto

import java.util.*

data class TripDateDto(val dateOut: Date, val flights: List<FlightDto>)

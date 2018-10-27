package eu.wawrzyczek.findflights.flights.search.dto

data class FlightDto(
    val infantsLeft: Int,
    val faresLeft: Int,
    val regularFare: RegularFareDto?,
    val flightNumber: String,
    val duration: String
)
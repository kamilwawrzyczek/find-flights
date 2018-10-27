package eu.wawrzyczek.findflights.flights.model

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.search.model.Station
import java.util.*

data class Flight(
    val date: SimpleDateTime, val flightNumber: String, val duration: String, val price: String, val currency: Currency,
    val origin: Station, val destination: Station, val infantsLeft: Int, val fareClass: String, val discountPercent: Int
) {
    val priceFormatted: String = price + currency.symbol
}

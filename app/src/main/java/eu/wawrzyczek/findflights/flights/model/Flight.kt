package eu.wawrzyczek.findflights.flights.model

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.search.model.Station
import java.text.DecimalFormat
import java.util.*

data class Flight(
    val date: SimpleDateTime,
    val flightNumber: String,
    val duration: String,
    val price: Map<FareType, Price>,
    val currency: Currency,
    val origin: Station,
    val destination: Station,
    val infantsLeft: Int,
    val fareClass: String
) {
    val priceFormatted: String by lazy {
        val format = DecimalFormat("0.00")
        val priceSum = price.values.map { it.amount }
            .reduce { a, b -> a + b }
        format.format(priceSum) + currency.symbol
    }
}

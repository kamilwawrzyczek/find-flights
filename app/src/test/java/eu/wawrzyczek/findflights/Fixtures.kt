package eu.wawrzyczek.findflights

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.flights.model.FareType
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.flights.model.Price
import eu.wawrzyczek.findflights.search.model.Station
import java.math.BigDecimal
import java.util.*

fun createFlight(
    dateTime: SimpleDateTime = SimpleDateTime(2015, 5, 12, 12, 35),
    price: Map<FareType, Price> = mapOf(FareType.ADT to Price(BigDecimal(12), 20)),
    currency: String = "PLN"
) = Flight(
    dateTime, "NR", "12:50", price, Currency.getInstance(currency), Station(), Station(),
    5, "W"
)
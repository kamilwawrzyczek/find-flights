package eu.wawrzyczek.findflights.flights.model

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.createFlight
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class FlightTest {
    @Test
    fun `priceFormatted should return sum of tickets price with currency symbol`() {
        val currency = Currency.getInstance("USD")
        val flight = createFlight(price = mapOf(FareType.ADT to Price(BigDecimal(12.5), 20, currency),
            FareType.CHD to Price(BigDecimal(7.5), 20, currency)
        ), currency = currency.currencyCode)

        assertEquals("20.00$", flight.priceFormatted)
    }

    @Test
    fun `priceFormatted should return sum of tickets price with currency code when symbol not available`() {
        val currency = Currency.getInstance("PLN")
        val flight = createFlight(price = mapOf(FareType.ADT to Price(BigDecimal(12.5), 20, currency),
            FareType.CHD to Price(BigDecimal(7.5), 20, currency)
        ), currency = currency.currencyCode)

        assertEquals("20.00PLN", flight.priceFormatted)
    }
}
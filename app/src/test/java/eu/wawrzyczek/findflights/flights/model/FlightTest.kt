package eu.wawrzyczek.findflights.flights.model

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.createFlight
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class FlightTest {
    @Test
    fun `priceFormatted should return sum of tickets price with currency symbol`() {
        val flight = createFlight(price = mapOf(FareType.ADT to Price(BigDecimal(12.5), 20),
            FareType.CHD to Price(BigDecimal(7.5), 20)
        ), currency = "USD")

        assertEquals("20.00$", flight.priceFormatted)
    }

    @Test
    fun `priceFormatted should return sum of tickets price with currency code when symbol not available`() {
        val flight = createFlight(price = mapOf(FareType.ADT to Price(BigDecimal(12.5), 20),
            FareType.CHD to Price(BigDecimal(7.5), 20)
        ), currency = "PLN")

        assertEquals("20.00PLN", flight.priceFormatted)
    }
}
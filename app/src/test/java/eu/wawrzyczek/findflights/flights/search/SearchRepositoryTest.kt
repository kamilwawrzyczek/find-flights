package eu.wawrzyczek.findflights.flights.search

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.flights.model.FareType
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.flights.model.Price
import eu.wawrzyczek.findflights.flights.search.dto.*
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class SearchRepositoryTest {
    private val fareDtoA = FareDto(FareType.CHD, 5, BigDecimal(12), 20)
    private val fareDtoB = FareDto(FareType.ADT, 1, BigDecimal(15), 25)
    private val regularFare = RegularFareDto("W", listOf(fareDtoA, fareDtoB))
    private val flightDto = FlightDto(1, 1, regularFare, "NR", "12:30")
    private val tripDateDto = TripDateDto(Date(0), listOf(flightDto))
    private val tripDtoA = TripDto("A", "AA", "B", "BB", listOf(tripDateDto))
    private val tripDtoB = TripDto("C", "CC", "D", "DD", listOf(tripDateDto))
    private val flightsDto = FlightsDto("PLN", listOf(tripDtoA, tripDtoB))
    private val searchData = SearchData(
        Station("A", "AA"), Station("B", "BB"),
        SimpleDate(2018, 1, 2), 1, 2, 3
    )

    private val client: SearchClient = mock {
        on { getFlights(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()) }.thenReturn(Single.just(flightsDto))
    }
    private val searchRepository = SearchRepository(client)

    @Test
    fun `getFlights should make request with correct parameters`() {
        searchRepository.getFlights(searchData).test().await()

        verify(client).getFlights("A", "B", "2018-01-02", 1, 2, 3,
            false, 3, 3, 3, 3)
    }

    @Test
    fun `getFlights should map dto into model class`() {
        val currency = Currency.getInstance("PLN")
        val date = SimpleDateTime(1970, 1, 1, 1, 0)
        val price = mapOf(
            FareType.ADT to Price(BigDecimal(15), 25, currency),
            FareType.CHD to Price(BigDecimal(60), 20, currency)
        )
        val expectedA = Flight(
            date, "NR", "12:30", price, currency,
            Station("A", "AA"), Station("B", "BB"), 1, "W"
        )
        val expectedB = expectedA.copy(origin = Station("C", "CC"), destination = Station("D", "DD"))

        val flightsTest = searchRepository.getFlights(searchData).test()
        flightsTest.assertValue(listOf(expectedA, expectedB))
    }
}
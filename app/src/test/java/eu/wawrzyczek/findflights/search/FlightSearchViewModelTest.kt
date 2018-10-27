package eu.wawrzyczek.findflights.search

import com.nhaarman.mockito_kotlin.mock
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.search.model.Station
import org.junit.Assert.*
import org.junit.Test

class FlightSearchViewModelTest {
    private val currentDate = SimpleDate(2018, 10, 27)
    private val dateProvider = mock<DateProvider> {
        on { getCurrentDate() }.thenReturn(currentDate)
    }
    private val flightSearchViewModel = FlightSearchViewModel(dateProvider)

    @Test
    fun `departureDate should contain current date on start`() {
        val date = flightSearchViewModel.departureDate.get()
        assertTrue(date == currentDate)
    }

    @Test
    fun `valid should return true when origin and destination are set and ticket count is greater then 0`() {
        flightSearchViewModel.origin.set(Station("PL", "Poland"))
        flightSearchViewModel.destination.set(Station("DE", "Germany"))
        assertTrue(flightSearchViewModel.valid.get())
    }

    @Test
    fun `valid should return false when origin is not set`() {
        flightSearchViewModel.origin.set(Station(name = "Poland"))
        flightSearchViewModel.destination.set(Station("DE", "Germany"))
        assertFalse(flightSearchViewModel.valid.get())
    }

    @Test
    fun `valid should return false when destination is not set`() {
        flightSearchViewModel.origin.set(Station("PL", "Poland"))
        flightSearchViewModel.destination.set(Station(name = "Poland"))
        assertFalse(flightSearchViewModel.valid.get())
    }

    @Test
    fun `valid should return false when ticket count is 0`() {
        flightSearchViewModel.origin.set(Station("PL", "Poland"))
        flightSearchViewModel.destination.set(Station("DE", "Germany"))
        flightSearchViewModel.adults.set(0)
        assertFalse(flightSearchViewModel.valid.get())
    }

    @Test
    fun `search should do noting when data is not valid`() {
        TODO("not implemented")
    }

    @Test
    fun `search should navigate to results activity with specified data`() {
        TODO("not implemented")
    }
}
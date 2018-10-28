package eu.wawrzyczek.findflights.flights

import androidx.databinding.Observable
import com.nhaarman.mockito_kotlin.*
import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.createFlight
import eu.wawrzyczek.findflights.flights.model.FareType
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.flights.model.Price
import eu.wawrzyczek.findflights.flights.search.SearchRepository
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.RuntimeException
import java.math.BigDecimal
import java.util.*

class FlightsViewModelTest {
    private val searchData = SearchData(Station(code = "DUB", name = "Dublin"), Station(code = "STN", name = "London Stansted"),
        SimpleDate(2015, 10, 5), 1, 0, 0)
    private val flight = createFlight()

    private val searchRepository : SearchRepository = mock {
        on { getFlights(searchData) }.thenReturn(Single.just(listOf(flight)))
    }
    private val navigator : AppNavigator = mock {  }
    private val flightsViewModel = FlightsViewModel(searchData, searchRepository, navigator)

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `error should return true when set`() {
        flightsViewModel.error.set(true)

        assertTrue(flightsViewModel.error.get())
    }

    @Test
    fun `error should return false when search is in progress`() {
        flightsViewModel.error.set(true)
        flightsViewModel.searching.set(true)

        assertFalse(flightsViewModel.error.get())
    }

    @Test
    fun `empty should return true when set`() {
        flightsViewModel.empty.set(true)

        assertTrue(flightsViewModel.empty.get())
    }

    @Test
    fun `empty should return false when search is in progress`() {
        flightsViewModel.empty.set(true)
        flightsViewModel.searching.set(true)

        assertFalse(flightsViewModel.empty.get())
    }

    @Test
    fun `empty should return false when error is set`() {
        flightsViewModel.empty.set(true)
        flightsViewModel.error.set(true)

        assertFalse(flightsViewModel.empty.get())
    }

    @Test
    fun `loadFlights should get flights from repository`() {
        val test = flightsViewModel.flights.test()

        flightsViewModel.loadFlights()

        test.assertValue(listOf(flight))
        verify(searchRepository).getFlights(searchData)
    }

    @Test
    fun `loadFlights should set progress to true on start and to false on stop`() {
        val values = mutableListOf<Boolean>()
        val onPropertyChangedCallback = mock<Observable.OnPropertyChangedCallback> {
            on { onPropertyChanged(any(), any()) }.then { _ ->
                values += flightsViewModel.searching.get()
                true
            }
        }
        flightsViewModel.searching.addOnPropertyChangedCallback(onPropertyChangedCallback)

        flightsViewModel.loadFlights()

        assertEquals(listOf(true, false), values)
    }

    @Test
    fun `loadFlights should clean error when success`() {
        flightsViewModel.error.set(true)

        flightsViewModel.loadFlights()

        assertFalse(flightsViewModel.error.get())
    }

    @Test
    fun `loadFlights should set error and return empty list when request fails `() {
        whenever(searchRepository.getFlights(searchData)).thenReturn(Single.error(RuntimeException()))

        val test = flightsViewModel.flights.test()
        flightsViewModel.loadFlights()

        test.assertValue(emptyList())
        assertTrue(flightsViewModel.error.get())
    }

    @Test
    fun `loadFlights should make request only once`() {
        val test = flightsViewModel.flights.test()

        flightsViewModel.loadFlights()
        flightsViewModel.loadFlights()

        test.assertValues(listOf(flight), listOf(flight))
        verify(searchRepository, times(1)).getFlights(searchData)
    }

    @Test
    fun `flight click should navigate to details activity`() {
        flightsViewModel.flightClick(flight)

        verify(navigator).navigateToFlightDetailsActivity(flight)
    }

    @Test
    fun `setting price filter should return flights with ticket price less then provided value`() {
        val test = flightsViewModel.flights.test()

        flightsViewModel.loadFlights()
        flightsViewModel.priceFilter.set(5)
        flightsViewModel.priceFilter.set(15)

        test.assertValues(listOf(flight), emptyList(), listOf(flight))
    }

    @Test
    fun `filter price == 0 should be treated as no filters applied`() {
        val test = flightsViewModel.flights.test()

        flightsViewModel.loadFlights()
        flightsViewModel.priceFilter.set(5)
        flightsViewModel.priceFilter.set(0)

        test.assertValues(listOf(flight), emptyList(), listOf(flight))
    }
}
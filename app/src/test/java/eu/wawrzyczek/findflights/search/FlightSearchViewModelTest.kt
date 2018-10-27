package eu.wawrzyczek.findflights.search

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import eu.wawrzyczek.findflights.common.AppNavigator
import eu.wawrzyczek.findflights.common.DateProvider
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.search.autocomplete.StationsRepository
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlightSearchViewModelTest {
    private val stations = listOf(Station(code = "a", name = "A"), Station(code = "b", name = "B"))
    private val currentDate = SimpleDate(2018, 10, 27)

    private val dateProvider = mock<DateProvider> {
        on { getCurrentDate() }.thenReturn(currentDate)
    }
    private val stationsRepository = mock<StationsRepository> {
        on { getStations() }.thenReturn(Single.just(stations))
    }
    private val navigator = mock<AppNavigator> {}
    private val flightSearchViewModel = FlightSearchViewModel(dateProvider, stationsRepository, navigator)

    @Before
    fun setUp() {
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

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
        flightSearchViewModel.search()
        verifyZeroInteractions(navigator)
    }

    @Test
    fun `search should navigate to results activity with specified data`() {
        val origin = Station("PL", "Poland")
        val destination = Station("DE", "Germany")
        flightSearchViewModel.origin.set(origin)
        flightSearchViewModel.destination.set(destination)
        flightSearchViewModel.children.set(5)

        flightSearchViewModel.search()
        verify(navigator).navigateToFlightActivity(SearchData(origin, destination, currentDate, 1, 0, 5))
    }

    @Test
    fun `stations should return stream with stations from repo`() {
        val test = flightSearchViewModel.stations.test()
        test.assertValue(stations)
    }

    @Test
    fun `stations should update stationsMap and trigger origin and destination update `() {
        val stationA = Station(name = stations[0].name)
        val stationB = Station(name = stations[1].name)
        flightSearchViewModel.origin.set(stationA)
        flightSearchViewModel.destination.set(stationB)

        flightSearchViewModel.stations.subscribe()

        assertFalse(flightSearchViewModel.origin.get()!!.code.isEmpty())
        assertFalse(flightSearchViewModel.destination.get()!!.code.isEmpty())

    }
}
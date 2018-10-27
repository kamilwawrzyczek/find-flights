package eu.wawrzyczek.findflights.flights

import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FlightsViewModelTest {
    private val searchData = SearchData(Station(code = "DUB", name = "Dublin"), Station(code = "STN", name = "London Stansted"),
        SimpleDate(2015, 10, 5), 1, 0, 0)
    private val flightsViewModel = FlightsViewModel(searchData)

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
    fun `flights should get flights from repository`() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun `flights should set progress to true on start and to false on stop`() {
        val testObserver = flightsViewModel.flights.test()

        assertTrue(flightsViewModel.searching.get())
        testObserver.await()
        assertFalse(flightsViewModel.searching.get())
    }

    @Test
    fun `flights should clean error when success`() {
        flightsViewModel.error.set(true)
        flightsViewModel.flights.test().await()
        assertFalse(flightsViewModel.error.get())
    }

    @Test
    fun `flights should set error when request fails and return empty list`() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun `flights should make request only once`() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
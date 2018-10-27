package eu.wawrzyczek.findflights.search.autocomplete

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import eu.wawrzyczek.findflights.search.autocomplete.dto.StationDto
import eu.wawrzyczek.findflights.search.autocomplete.dto.StationsDto
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import org.junit.Test

class StationsRepositoryTest {
    private val stationsDto = listOf(StationDto("A", "a", "b"))
    private val stationsClient = mock<StationsClient> {
        on { getStations() }.thenReturn(Single.just(StationsDto(stationsDto)))
    }
    private val stationsRepository = StationsRepository(stationsClient)

    @Test
    fun `getStations should return stations from web`() {
        val stations = stationsDto.map { Station(it.code, it.name, it.alternateName) }
        val test = stationsRepository.getStations().test()
        test.assertValue(stations)
    }

    @Test
    fun `getStations should cache response`() {
        val stations = stationsDto.map { Station(it.code, it.name, it.alternateName) }

        stationsRepository.getStations().test().await()
        val test = stationsRepository.getStations().test()

        test.assertValue(stations)
        verify(stationsClient, times(1)).getStations()
    }
}
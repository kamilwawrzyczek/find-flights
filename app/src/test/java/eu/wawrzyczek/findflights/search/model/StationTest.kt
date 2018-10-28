package eu.wawrzyczek.findflights.search.model

import org.junit.Assert.*
import org.junit.Test

class StationTest {
    @Test
    fun `station should be valid when name and code are not empty`() {
        val station = Station(code = "A", name = "B")
        assertTrue(station.valid)
    }

    @Test
    fun `station is not valid when name is empty`() {
        val station = Station(code = "A", name = "")
        assertFalse(station.valid)
    }

    @Test
    fun `station is not valid when code is empty`() {
        val station = Station(code = "", name = "B")
        assertFalse(station.valid)
    }
}
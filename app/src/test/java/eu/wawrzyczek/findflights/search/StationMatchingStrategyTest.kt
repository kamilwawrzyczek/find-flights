package eu.wawrzyczek.findflights.search

import eu.wawrzyczek.findflights.filtering.MatchingStrategy
import eu.wawrzyczek.findflights.search.model.Station
import org.junit.Assert.*
import org.junit.Test

class StationMatchingStrategyTest {
    private val matchingStrategy = StationMatchingStrategy()

    @Test
    fun `station should match when name contains query`() {
        val station =  Station(name = "baaaab", alternateName = "ccc")
        assertTrue(matchingStrategy.match("aaa", station))
    }

    @Test
    fun `station should match when alternativeName contains query`() {
        val station =  Station(name = "ccc", alternateName = "baaaab")
        assertTrue(matchingStrategy.match("aaa", station))
    }

    @Test
    fun `station should not match when name and alternative name do not contains query`() {
        val station =  Station(name = "bbbb", alternateName = "cccc")
        assertFalse(matchingStrategy.match("aaa", station))
    }

    @Test
    fun `station should match name ignoring case`() {
        val station =  Station(name = "baaaab", alternateName = "ccc")
        assertTrue(matchingStrategy.match("AAA", station))
        val stationB =  Station(name = "bAaAAb", alternateName = "ccc")
        assertTrue(matchingStrategy.match("aaa", stationB))
    }

    @Test
    fun `station should math alternativeName ignoring case`() {
        val station =  Station(name = "ccc", alternateName = "baaaab")
        assertTrue(matchingStrategy.match("AAA", station))
        val stationB =  Station(name = "ccc", alternateName = "bAaAAb")
        assertTrue(matchingStrategy.match("aaa", stationB))
    }
}
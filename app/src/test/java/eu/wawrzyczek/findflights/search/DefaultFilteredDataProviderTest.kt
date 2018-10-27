package eu.wawrzyczek.findflights.search

import eu.wawrzyczek.findflights.filtering.DefaultFilteredDataProvider
import eu.wawrzyczek.findflights.filtering.MatchingStrategy
import org.junit.Assert.assertTrue
import org.junit.Test

class DefaultFilteredDataProviderTest {
    private val data = listOf("aaa", "bbb", "ccc")
    private val filteredDataProvider = DefaultFilteredDataProvider(data, object : MatchingStrategy<String> {
        override fun match(query: String, data: String): Boolean = data == query
    })

    @Test
    fun `getFilteredData should return only matching stations`() {
        val filteredData = filteredDataProvider.getFilteredData("aaa")
        assertTrue(filteredData.size == 1)
        assertTrue(filteredData[0] == "aaa")
    }

    @Test
    fun `getFilteredData should return empty list when data not found`() {
        val filteredData = filteredDataProvider.getFilteredData("ddd")
        assertTrue(filteredData.isEmpty())
    }
}
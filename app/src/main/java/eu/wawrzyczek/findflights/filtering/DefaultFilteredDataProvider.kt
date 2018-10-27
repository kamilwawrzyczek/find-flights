package eu.wawrzyczek.findflights.filtering

class DefaultFilteredDataProvider<T>(private val stations: List<T>, private val matchingStrategy: MatchingStrategy<T>) : FilteredDataProvider<T> {
    override fun getFilteredData(query: String): List<T> {
        return stations.filter { matchingStrategy.match(query, it) }
    }
}

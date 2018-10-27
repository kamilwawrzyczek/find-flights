package eu.wawrzyczek.findflights.filtering

interface FilteredDataProvider<T> {
    fun getFilteredData(query: String): List<T>
}
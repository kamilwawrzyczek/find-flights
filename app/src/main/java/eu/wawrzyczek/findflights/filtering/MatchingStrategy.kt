package eu.wawrzyczek.findflights.filtering

interface MatchingStrategy<T> {
    fun match(query: String, data: T): Boolean
}
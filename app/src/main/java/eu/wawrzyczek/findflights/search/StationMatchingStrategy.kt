package eu.wawrzyczek.findflights.search

import eu.wawrzyczek.findflights.filtering.MatchingStrategy
import eu.wawrzyczek.findflights.search.model.Station

class StationMatchingStrategy : MatchingStrategy<Station> {
    override fun match(query: String, data: Station): Boolean = data.name.toLowerCase().contains(query.toLowerCase()) ||
            data.alternateName?.toLowerCase()?.contains(query.toLowerCase()) == true
}

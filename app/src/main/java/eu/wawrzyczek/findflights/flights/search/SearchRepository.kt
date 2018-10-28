package eu.wawrzyczek.findflights.flights.search

import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.flights.model.Price
import eu.wawrzyczek.findflights.flights.search.dto.FlightsDto
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.Single
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class SearchRepository(private val client: SearchClient) {
    fun getFlights(searchData: SearchData): Single<List<Flight>> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.format(searchData.departureDate.date)
        return client.getFlights(
            searchData.origin.code, searchData.destination.code, date, searchData.adults,
            searchData.teens, searchData.children
        ).map { convert(it) }
    }

    private fun convert(flightsDto: FlightsDto) : List<Flight> {
        return flightsDto.trips.map { tripDto ->
            tripDto.dates.map { tripDateDto ->
                tripDateDto.flights
                    .asSequence()
                    .filter { it.regularFare != null }
                    .map { flightDto ->
                        val currency = Currency.getInstance(flightsDto.currency)
                        val priceMap = flightDto.regularFare!!.fares
                            .associate { it.type to Price(it.amount.multiply(BigDecimal(it.count)), it.discountInPercent, currency) }
                        val fareClass = flightDto.regularFare.fareClass
                        val duration = flightDto.duration
                        val flightNumber = flightDto.flightNumber
                        val infantsLeft = flightDto.infantsLeft
                        val dateOut = convertDateToSimpleDateTime(tripDateDto.dateOut)
                        val origin = Station(code = tripDto.origin, name = tripDto.originName)
                        val destination = Station(code = tripDto.destination, name = tripDto.destinationName)
                        Flight(dateOut, flightNumber, duration, priceMap, currency, origin, destination, infantsLeft, fareClass)
                    }
                    .toList()
            }.flatten()
        }.flatten()
    }

    private fun convertDateToSimpleDateTime(date: Date) : SimpleDateTime {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return SimpleDateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }
}
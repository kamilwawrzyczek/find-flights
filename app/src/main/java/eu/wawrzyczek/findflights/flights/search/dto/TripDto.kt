package eu.wawrzyczek.findflights.flights.search.dto

data class TripDto(val origin: String, val originName:String, val destination:String, val destinationName: String,
                   val dates : List<TripDateDto>)

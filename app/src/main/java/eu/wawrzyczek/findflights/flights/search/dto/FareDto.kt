package eu.wawrzyczek.findflights.flights.search.dto

import eu.wawrzyczek.findflights.flights.model.FareType
import java.math.BigDecimal

data class FareDto(val type : FareType, val count: Int, val amount: BigDecimal, val discountInPercent: Int)
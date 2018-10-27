package eu.wawrzyczek.findflights.search

import android.app.DatePickerDialog
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.common.AutocompleteAdapter
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.common.setTextListener
import eu.wawrzyczek.findflights.databinding.ActivityFlightSearchBinding
import eu.wawrzyczek.findflights.filtering.DefaultFilteredDataProvider
import eu.wawrzyczek.findflights.search.model.Station
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MAX_TICKETS_IN_ONE_SEARCH = 10

class FlightSearchActivity : AppCompatActivity() {
    private val flightSearchViewModel: FlightSearchViewModel by viewModel()
    private val stationMatchingStrategy: StationMatchingStrategy by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightSearchBinding>(this, R.layout.activity_flight_search)
        binding.vm = flightSearchViewModel
        binding.origin.setTextListener { flightSearchViewModel.origin.set(Station(name = it)) }
        binding.destination.setTextListener { flightSearchViewModel.origin.set(Station(name = it)) }
        binding.departureDate.setOnClickListener { _ ->
            showDatePicker(flightSearchViewModel.departureDate.get()!!) {
                flightSearchViewModel.departureDate.set(it)
            }
        }
        binding.adults.adapter = createSimpleIntAdapter()
        binding.teens.adapter = createSimpleIntAdapter()
        binding.children.adapter = createSimpleIntAdapter()
        prepareAutocompleteAdapter(binding)
    }

    private fun prepareAutocompleteAdapter(binding: ActivityFlightSearchBinding) {
        val stations = listOf(Station(name = "aaaa"), Station(name = "bbb"))
        val dataProvider = DefaultFilteredDataProvider(stations, stationMatchingStrategy)
        binding.origin.setAdapter(AutocompleteAdapter(dataProvider))
        binding.destination.setAdapter(AutocompleteAdapter(dataProvider))
    }

    private fun showDatePicker(date: SimpleDate, callback: (SimpleDate) -> Unit) {
        DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                callback.invoke(SimpleDate(year, month, dayOfMonth))
            }, date.year, date.month, date.dayOfMonth
        ).show()
    }

    private fun createSimpleIntAdapter(maxValue: Int = MAX_TICKETS_IN_ONE_SEARCH, minValue: Int = 0) =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, (minValue..maxValue).toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
}

package eu.wawrzyczek.findflights.search

import android.app.DatePickerDialog
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.common.AutocompleteAdapter
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.common.plusAssign
import eu.wawrzyczek.findflights.common.setTextListener
import eu.wawrzyczek.findflights.databinding.ActivityFlightSearchBinding
import eu.wawrzyczek.findflights.filtering.DefaultFilteredDataProvider
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MAX_TICKETS_IN_ONE_SEARCH = 10

class FlightSearchActivity : AppCompatActivity() {
    private val flightSearchViewModel: FlightSearchViewModel by viewModel()
    private val stationMatchingStrategy: StationMatchingStrategy by inject()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightSearchBinding>(this, R.layout.activity_flight_search)
        binding.vm = flightSearchViewModel
        binding.origin.setTextListener { flightSearchViewModel.origin.set(Station(name = it)) }
        binding.destination.setTextListener { flightSearchViewModel.destination.set(Station(name = it)) }
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

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    private fun prepareAutocompleteAdapter(binding: ActivityFlightSearchBinding) {
        disposables += flightSearchViewModel.stations.subscribe({ stations ->
            val dataProvider = DefaultFilteredDataProvider(stations, stationMatchingStrategy)
            binding.origin.setAdapter(AutocompleteAdapter(dataProvider))
            binding.destination.setAdapter(AutocompleteAdapter(dataProvider))
        }, {_ ->
            Snackbar.make(binding.root, R.string.stations_download_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { prepareAutocompleteAdapter(binding) }
                .show()
        })
    }

    private fun showDatePicker(date: SimpleDate, callback: (SimpleDate) -> Unit) {
        DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                callback.invoke(SimpleDate(year, month + 1, dayOfMonth))
            }, date.year, date.month - 1, date.dayOfMonth
        ).show()
    }

    private fun createSimpleIntAdapter(maxValue: Int = MAX_TICKETS_IN_ONE_SEARCH, minValue: Int = 0) =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, (minValue..maxValue).toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
}

package eu.wawrzyczek.findflights.search

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.common.SimpleDate
import eu.wawrzyczek.findflights.databinding.ActivityFlightSearchBinding

const val MAX_TICKETS_IN_ONE_SEARCH = 10

class FlightSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightSearchBinding>(this, R.layout.activity_flight_search)
        binding.departureDate.setOnClickListener { _ ->
            showDatePicker {
                // todo handle data
            }
        }
        binding.adults.adapter = createSimpleIntAdapter()
        binding.teens.adapter = createSimpleIntAdapter()
        binding.children.adapter = createSimpleIntAdapter()
        //TODO add autocomplete behavior
    }

    private fun showDatePicker(callback: (SimpleDate) -> Unit) {
        DatePickerDialog(
            this, { _, year, month, dayOfMonth ->
                callback.invoke(SimpleDate(year, month, dayOfMonth))
            }, 5, 1, 1992
        ).show()
    }

    private fun createSimpleIntAdapter(maxValue: Int = MAX_TICKETS_IN_ONE_SEARCH, minValue: Int = 0) =
        ArrayAdapter(this, android.R.layout.simple_spinner_item, (minValue..maxValue).toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
}

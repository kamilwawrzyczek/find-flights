package eu.wawrzyczek.findflights.flights.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.databinding.ActivityFlightDetailsBinding
import eu.wawrzyczek.findflights.flights.model.FareType
import eu.wawrzyczek.findflights.flights.model.Flight

private const val FLIGHT_DATA = "flight_data"
fun startActivity(context: Context, flight: Flight) {
    context.startActivity(Intent(context, FlightDetailsActivity::class.java).apply {
        putExtra(FLIGHT_DATA, flight)
    })
}

class FlightDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightDetailsBinding>(this, R.layout.activity_flight_details)
        binding.flight = intent.getParcelableExtra(FLIGHT_DATA)
        binding.adultPrice = binding.flight?.price?.get(FareType.ADT)
        binding.childsPrice = binding.flight?.price?.get(FareType.CHD)
        binding.teensPrice = binding.flight?.price?.get(FareType.TEEN)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
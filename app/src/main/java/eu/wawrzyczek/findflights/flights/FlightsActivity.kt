package eu.wawrzyczek.findflights.flights

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.databinding.ActivityFlightsBinding
import eu.wawrzyczek.findflights.search.model.SearchData

private const val SEARCH_DATA = "search_data"

fun startActivity(context: Context, searchData: SearchData) {
    context.startActivity(Intent(context, FlightsActivity::class.java).apply {
        putExtra(SEARCH_DATA, searchData)
    })
}

class FlightsActivity : AppCompatActivity() {
    private val flightsAdapter by lazy { FlightsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightsBinding>(this, R.layout.activity_flights)
        prepareRecyclerView(binding)
//        val data = intent.getParcelableExtra<SearchData>(SEARCH_DATA)
//        title = data.origin.name + " > " + data.destination.name
    }

    private fun prepareRecyclerView(binding: ActivityFlightsBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = flightsAdapter
    }
}

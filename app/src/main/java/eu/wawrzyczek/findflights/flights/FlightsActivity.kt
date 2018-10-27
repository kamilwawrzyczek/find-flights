package eu.wawrzyczek.findflights.flights

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.common.SimpleDateTime
import eu.wawrzyczek.findflights.common.plusAssign
import eu.wawrzyczek.findflights.databinding.ActivityFlightsBinding
import eu.wawrzyczek.findflights.flights.model.Flight
import eu.wawrzyczek.findflights.search.model.SearchData
import eu.wawrzyczek.findflights.search.model.Station
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

private const val SEARCH_DATA = "search_data"

fun startActivity(context: Context, searchData: SearchData) {
    context.startActivity(Intent(context, FlightsActivity::class.java).apply {
        putExtra(SEARCH_DATA, searchData)
    })
}

class FlightsActivity : AppCompatActivity() {
    private val flightsViewModel by viewModel<FlightsViewModel> {
        parametersOf(intent.getParcelableExtra<SearchData>(SEARCH_DATA))
    }
    private val flightsAdapter by lazy { FlightsAdapter { flightsViewModel.flightClick(it) } }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityFlightsBinding>(this, R.layout.activity_flights)
        binding.vm = flightsViewModel
        prepareRecyclerView(binding)//todo try again clicklistener

        val data = flightsViewModel.searchData
        title = data.origin.name + " > " + data.destination.name
    }

    override fun onStop() {
        super.onStop()
        disposables.dispose()
    }

    private fun prepareRecyclerView(binding: ActivityFlightsBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = flightsAdapter
        disposables += flightsViewModel.flights
            .subscribe { items -> flightsAdapter.updateItems(items) }
    }
}

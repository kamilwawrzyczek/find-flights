package eu.wawrzyczek.findflights.flights

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import eu.wawrzyczek.findflights.search.model.SearchData

private const val SEARCH_DATA = "search_data"

fun startActivity(context: Context, searchData: SearchData) {
    context.startActivity(Intent(context, FlightsActivity::class.java).apply {
        putExtra(SEARCH_DATA, searchData)
    })
}

class FlightsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getParcelableExtra<SearchData>(SEARCH_DATA)
        title = data.origin.name + " > " + data.destination.name
    }
}

package eu.wawrzyczek.findflights.flights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.databinding.ViewFlightItemBinding

class FlightsAdapter : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {
    private val items = listOf("a", "B")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_flight_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(binding: ViewFlightItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            //todo bind item
        }
    }
}

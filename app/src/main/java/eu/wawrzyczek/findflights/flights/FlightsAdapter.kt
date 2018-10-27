package eu.wawrzyczek.findflights.flights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import eu.wawrzyczek.findflights.R
import eu.wawrzyczek.findflights.databinding.ViewFlightItemBinding
import eu.wawrzyczek.findflights.flights.model.Flight

typealias ClickListener = (Flight) -> Unit

class FlightsAdapter(private val clickListener: ClickListener) : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {
    private var items = emptyList<Flight>()

    fun updateItems(items:List<Flight>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(DataBindingUtil.inflate(inflater, R.layout.view_flight_item, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
    }

    class ViewHolder(private val binding: ViewFlightItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(flight: Flight, clickListener: ClickListener) {
            binding.data = flight
            binding.root.setOnClickListener { clickListener.invoke(flight) }
            binding.executePendingBindings()
        }
    }
}

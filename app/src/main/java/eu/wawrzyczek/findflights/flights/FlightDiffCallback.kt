package eu.wawrzyczek.findflights.flights

import androidx.recyclerview.widget.DiffUtil
import eu.wawrzyczek.findflights.flights.model.Flight

class FlightDiffCallback(private val oldList: List<Flight>, private val newList: List<Flight>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.flightNumber == newItem.flightNumber
    }

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}
package eu.wawrzyczek.findflights.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import eu.wawrzyczek.findflights.filtering.FilteredDataProvider

class AutocompleteAdapter<T>(private val dataProvider: FilteredDataProvider<T>) : BaseAdapter(), Filterable {
    private var items = emptyList<T>()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(query: CharSequence?): FilterResults? {
            return query?.toString()?.let {
                FilterResults().apply {
                    values = dataProvider.getFilteredData(it)
                    count = (values as List<T>).size
                }
            }
        }

        override fun publishResults(query: CharSequence?, results: FilterResults?) {
            results?.let {
                items = it.values as List<T>
                notifyDataSetChanged()
            }
        }
    }

    override fun getView(position: Int, oldView: View?, parent: ViewGroup): View {
        val view = (oldView ?: LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_dropdown_item_1line, parent, false
        ))
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = items[position].toString()
        return view
    }

    override fun getItem(position: Int): Any = items[position].toString()

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

}

package com.treplabs.ddm.ddmapp

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import timber.log.Timber

class FilterableLocalItemsAdapter<T: FilterableDataClass>(context: Context, val backingList: List<T>) :
    ArrayAdapter<T>(context, android.R.layout.simple_dropdown_item_1line), Filterable {

    var filteredItems: List<T> = backingList.toMutableList()

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(index: Int): T {
        return filteredItems[index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                Timber.d("Get Filtered items")
                val filterResults = FilterResults()
                if (constraint != null) { // Retrieve the autocomplete results.
                    filteredItems = backingList.filter { it.getFilterKey().contains(constraint, true) }
                    filterResults.values = filteredItems
                    filterResults.count = filteredItems.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    Timber.d("Filtered result is probably zero?")
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
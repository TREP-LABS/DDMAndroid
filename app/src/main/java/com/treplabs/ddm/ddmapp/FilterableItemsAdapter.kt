package com.treplabs.ddm.ddmapp

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.treplabs.ddm.ddmapp.datasources.FilterableDataSource

class FilterableItemsAdapter<T>(context: Context, val dataSource: FilterableDataSource<T>) :
    ArrayAdapter<T>(context, android.R.layout.simple_dropdown_item_1line), Filterable {
    private var resultList: List<T>? = null

    override fun getCount(): Int {
        return resultList!!.size
    }

    override fun getItem(index: Int): T {
        return resultList!![index]
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) { // Retrieve the autocomplete results.
                    resultList = dataSource.getFilteredItems(constraint.toString())
                    filterResults.values = resultList
                    filterResults.count = resultList!!.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}
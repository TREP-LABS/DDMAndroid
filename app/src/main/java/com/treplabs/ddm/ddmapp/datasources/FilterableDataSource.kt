package com.treplabs.ddm.ddmapp.datasources

interface FilterableDataSource<T> {
    fun getFilteredItems(queryText: String): List<T>
}
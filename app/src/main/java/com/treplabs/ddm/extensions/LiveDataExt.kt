package com.treplabs.ddm.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner, Observer { v -> observer(v) })
}

inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) = observe(owner, Observer { it?.let { observer(it) } })

package com.treplabs.ddm.extensions

import android.widget.EditText
import android.widget.TextView

/**
 * Created by Rasheed Sulayman.
 */

fun TextView.isEmpty() = text.isNullOrEmpty()

fun TextView.validate(errorMessage: String = "This Field is required"): Boolean {
    if (!isEmpty()) return true
    error = errorMessage
    return false
}

fun EditText.stringContent(): String = text.toString()
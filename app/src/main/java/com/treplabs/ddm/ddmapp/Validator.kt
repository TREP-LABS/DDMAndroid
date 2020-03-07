package com.treplabs.ddm.ddmapp

import android.widget.TextView
import com.treplabs.ddm.extensions.validate

/**
 * Created by Rasheed Sulayman.
 */

fun validateTextLayouts(vararg textLayouts: TextView): Boolean {
    clearTextLayoutError(*textLayouts)
    for (textLayout in textLayouts) {
        if (!textLayout.validate()) return false
    }
    return true
}

fun clearTextLayoutError(vararg textLayouts: TextView) {
    for (textLayout in textLayouts) textLayout.error = null
}

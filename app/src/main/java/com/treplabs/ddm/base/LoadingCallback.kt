package com.treplabs.ddm.base

import androidx.annotation.StringRes

/**
 * Created by Rasheed Sulayman.
 */
interface LoadingCallback {

    fun showLoading()

    fun showLoading(@StringRes resId: Int)

    fun showLoading(message: String)

    fun dismissLoading()

    fun showError(@StringRes resId: Int)

    fun showError(message: String)

    fun isLoading(): Boolean
}

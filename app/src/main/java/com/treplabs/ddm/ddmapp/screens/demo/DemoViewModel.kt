package com.treplabs.ddm.ddmapp.screens.demo

import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import javax.inject.Inject

class DemoViewModel @Inject constructor(
    private val prefsValueHelper: PrefsValueHelper
) : BaseViewModel() {

    fun markDemoShown() {
        prefsValueHelper.setDemoShownStatus(true)
    }

    override fun addAllLiveDataToObservablesList() {
    }

}


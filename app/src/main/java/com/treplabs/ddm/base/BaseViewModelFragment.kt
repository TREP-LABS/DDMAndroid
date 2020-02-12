package com.treplabs.ddm.base

import androidx.lifecycle.Observer
import com.treplabs.ddm.networkutils.LoadingStatus

/**
 * Created by Rasheed Sulayman.
 */
abstract class BaseViewModelFragment : BaseFragment() {

    override fun onStart() {
        super.onStart()
        if (getViewModel().loadingStatus.hasObservers()) return
        getViewModel().loadingStatus.observe(this, Observer {
            when (it) {
                LoadingStatus.Success -> mainActivity.dismissLoading()
                is LoadingStatus.Loading -> mainActivity.showLoading(it.message)
                is LoadingStatus.Error -> {
                    mainActivity.showError(it.errorMessage)
                    getViewModel().errorShown()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getViewModel().addAllLiveDataToObservablesList()
        for (liveData in getViewModel().observablesList) liveData.removeObservers(this)
    }

    abstract fun getViewModel(): BaseViewModel
}

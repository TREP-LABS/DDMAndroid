package com.treplabs.ddm.base

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.treplabs.ddm.App
import com.treplabs.ddm.MainActivity
import com.treplabs.ddm.di.AppComponent
import com.treplabs.ddm.networkutils.LoadingStatus

/**
 * Created by Rasheed Sulayman.
 */
abstract class BaseViewModelDialogFragment : DialogFragment() {

    protected val mainActivity: MainActivity
        get() {
            return activity as? MainActivity ?: throw IllegalStateException("Not attached!")
        }

    protected val daggerAppComponent: AppComponent
        get() = (mainActivity.applicationContext as App).component

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

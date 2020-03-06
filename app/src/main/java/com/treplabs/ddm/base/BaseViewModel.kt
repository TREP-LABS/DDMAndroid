package com.treplabs.ddm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treplabs.ddm.networkutils.LoadingStatus
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rasheed Sulayman.
 */
abstract class BaseViewModel : ViewModel() {

    protected val disposeBag = CompositeDisposable()

    val observablesList: MutableList<LiveData<*>> = mutableListOf()

    protected val _loadingStatus = MutableLiveData<LoadingStatus>()

    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    fun errorShown() {
        _loadingStatus.value = null
    }

    abstract fun addAllLiveDataToObservablesList()

    protected fun addAllLiveDataToObservablesList(vararg liveData: LiveData<*>) = observablesList.addAll(liveData)

    protected fun nullifyLiveDataValues(vararg liveDataValues: MutableLiveData<*>) {
        for (liveData in liveDataValues) liveData.value = null
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}

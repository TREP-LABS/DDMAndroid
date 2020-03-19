package com.treplabs.ddm.ddmapp.screens.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ChooseConditionViewModel @Inject constructor(
    private val conditionsRepository: ConditionsRepository
) : BaseViewModel() {

    private val _recommendedCondition = MutableLiveData<Event<Condition>>()

    val recommendedCondition: LiveData<Event<Condition>>
        get() = _recommendedCondition

    fun getCondition(conditionKey: String) {
        _loadingStatus.value = LoadingStatus.Loading("Getting recommendations, please wait")
        conditionsRepository.getCondition(conditionKey).subscribeBy {
            when (it) {
                is Result.Success -> {
                    _recommendedCondition.value = Event(it.data)
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
            }
        }.disposeBy(disposeBag)
    }

    override fun addAllLiveDataToObservablesList() {
    }

}


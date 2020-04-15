package com.treplabs.ddm.ddmapp.screens.symptoms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SymptomsViewModel @Inject constructor(
    private val conditionsRepository: ConditionsRepository
) : BaseViewModel() {

    private val _recommendedCondition = MutableLiveData<Event<Condition>>()

    val recommendedCondition: LiveData<Event<Condition>>
        get() = _recommendedCondition

    override fun addAllLiveDataToObservablesList() {
    }

    fun getRecommendedCondition(symptoms: Set<Symptom>) {
        _loadingStatus.value = LoadingStatus.Loading("Please wait . . .")
        computeRecommendedCondition(symptoms).flatMap {
            conditionsRepository.getCondition(it)
        }.subscribeBy {
            when (it) {
                is Result.Success -> {
                    _recommendedCondition.value = Event(it.data)
                    _loadingStatus.value = LoadingStatus.Success
                }
                is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
            }
        }.disposeBy(disposeBag)
    }

    private fun computeRecommendedCondition(symptoms: Set<Symptom>): Single<String> {
        return Single.fromCallable {
            val map = mutableMapOf<String, Int>()
            symptoms.forEach { symptom ->
                symptom.conditionKeys!!.forEach { conditionKey ->
                    if (!map.containsKey(conditionKey)) map[conditionKey] = 0
                    var value = map[conditionKey]!!
                    map[conditionKey] = ++value
                }
            }
            var highestKey: String? = null
            for (key in map.keys) {
                if ((highestKey == null) || (map[highestKey]!! < map[key]!!)) {
                    highestKey = key
                }
            }
            //TODO improve the function to return set of top functions, or probably even rank
            highestKey!!
        }.subscribeOn(Schedulers.computation())
    }
}


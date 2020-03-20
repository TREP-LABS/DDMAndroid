package com.treplabs.ddm.ddmapp.screens.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.Symptom
import javax.inject.Inject


class FilterableDataSharedViewModel @Inject constructor() : ViewModel() {

    private val _conditions = MutableLiveData<List<Condition>>()
    val conditions: LiveData<List<Condition>>
        get() = _conditions

    private val _symptoms = MutableLiveData<List<Symptom>>()
    val symptoms: LiveData<List<Symptom>>
        get() = _symptoms

    fun setConditions(conditionList: List<Condition>) {
        _conditions.value = conditionList
    }

    fun setSymptoms(symptomList: List<Symptom>) {
        _symptoms.value = symptomList
    }

}

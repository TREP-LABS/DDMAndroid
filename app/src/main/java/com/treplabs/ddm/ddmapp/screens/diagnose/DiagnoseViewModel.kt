package com.treplabs.ddm.ddmapp.screens.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.datasources.repositories.SymptomsRepository
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject


class DiagnoseViewModel @Inject constructor(
    private val conditionsRepository: ConditionsRepository,
    private val symptomsRepository: SymptomsRepository,
    private val authRepository: FirebaseAuthRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user

    private val _symptoms = MutableLiveData<Event<List<Symptom>>>()
    val symptoms: LiveData<Event<List<Symptom>>>
        get() = _symptoms

    private val _conditions = MutableLiveData<Event<List<Condition>>>()
    val conditions: LiveData<Event<List<Condition>>>
        get() = _conditions

    fun reloadUser() {
        _user.value = authRepository.getUser()
    }

    fun getConditions() {
        _loadingStatus.value = LoadingStatus.Loading("Please wait...")
        conditionsRepository.getAllConditions()
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _conditions.value = Event(it.data)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    fun getSymptoms() {
        _loadingStatus.value = LoadingStatus.Loading("Please wait...")
        symptomsRepository.getAllSymptoms()
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _symptoms.value = Event(it.data)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    override fun addAllLiveDataToObservablesList() {
    }
}

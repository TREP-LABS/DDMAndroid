package com.treplabs.ddm.ddmapp.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.Singles
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val prefsValueHelper: PrefsValueHelper
) : BaseViewModel() {

    private val _signUpComplete = MutableLiveData<Event<Boolean>>()

    val signUpComplete: LiveData<Event<Boolean>>
        get() = _signUpComplete

    fun signUpWithPassword(signUpRequest: SignUpRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Signing up, please wait")
        firebaseAuthRepository.signUpWithPassWord(signUpRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        prefsValueHelper.setLastSignedInEmail(signUpRequest.email)
                        _signUpComplete.value = Event(true)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    override fun addAllLiveDataToObservablesList() {
    }
}
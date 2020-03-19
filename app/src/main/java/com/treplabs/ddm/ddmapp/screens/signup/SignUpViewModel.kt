package com.treplabs.ddm.ddmapp.screens.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : BaseViewModel() {

    private val _signUpComplete = MutableLiveData<Event<Boolean>>()

    val signUpComplete: LiveData<Event<Boolean>>
        get() = _signUpComplete

    fun signUpWithPassword(signUpRequest: SignInRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Signing in, please wait")
        firebaseAuthRepository.signUpWithPassWord(signUpRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
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
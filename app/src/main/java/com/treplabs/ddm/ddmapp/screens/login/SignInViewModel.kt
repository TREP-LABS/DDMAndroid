package com.treplabs.ddm.ddmapp.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.repositories.FirebaseAuthRepository
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : BaseViewModel() {

    private val _signInComplete = MutableLiveData<Event<FirebaseUser>>()

    val signInComplete: LiveData<Event<FirebaseUser>>
        get() = _signInComplete

    fun signIn(signUpRequest: SignInRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Signing in, please wait")
        firebaseAuthRepository.signInWithPassword(signUpRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _signInComplete.value = Event(it.data)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    override fun addAllLiveDataToObservablesList() {
    }
}

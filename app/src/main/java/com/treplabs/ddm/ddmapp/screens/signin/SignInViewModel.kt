package com.treplabs.ddm.ddmapp.screens.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : BaseViewModel() {

    private val _signInComplete = MutableLiveData<Event<Boolean>>()

    val signInComplete: LiveData<Event<Boolean>>
        get() = _signInComplete

    fun signInWithPassword(signUpRequest: SignInRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Signing in, please wait")
        firebaseAuthRepository.signInWithPassword(signUpRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _signInComplete.value = Event(true)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    fun signInWithGoogle(acct: GoogleSignInAccount) {
        _loadingStatus.value = LoadingStatus.Loading("Signing in, please wait")
        firebaseAuthRepository.signInWithGoogle(acct)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        _signInComplete.value = Event(true)
                        _loadingStatus.value = LoadingStatus.Success
                    }
                    is Result.Error -> _loadingStatus.value = LoadingStatus.Error(it.errorCode, it.errorMessage)
                }
            }.disposeBy(disposeBag)
    }

    override fun addAllLiveDataToObservablesList() {
    }
}

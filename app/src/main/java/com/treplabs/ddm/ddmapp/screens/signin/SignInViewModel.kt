package com.treplabs.ddm.ddmapp.screens.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.screens.signin.NavigationFlow.RETURNING_USER
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

enum class NavigationFlow { NEW_USER, RETURNING_USER }

class SignInViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    private val prefsValueHelper: PrefsValueHelper
) : BaseViewModel() {

    private val _user = MutableLiveData<FirebaseUser>(FirebaseAuth.getInstance().currentUser)

    val user: LiveData<FirebaseUser>
        get() = _user

    private val _lastSignedInEmail = MutableLiveData<String>(prefsValueHelper.getLastSignedInEmail())

    val lastSignedInEmail: LiveData<String>
        get() = _lastSignedInEmail

    private val _navigationFlow = MutableLiveData<Event<NavigationFlow>>()
    val navigationFlow: LiveData<Event<NavigationFlow>>
        get() = _navigationFlow


    private val _signInComplete = MutableLiveData<Event<Boolean>>()

    val signInComplete: LiveData<Event<Boolean>>
        get() = _signInComplete

    init {
        _navigationFlow.value =
            Event(if (prefsValueHelper.getIsDemoShown()) RETURNING_USER else NavigationFlow.NEW_USER)
    }

    fun signInWithPassword(signInRequest: SignInRequest) {
        _loadingStatus.value = LoadingStatus.Loading("Signing in, please wait")
        firebaseAuthRepository.signInWithPassword(signInRequest)
            .subscribeBy {
                when (it) {
                    is Result.Success -> {
                        prefsValueHelper.setLastSignedInEmail(signInRequest.email)
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

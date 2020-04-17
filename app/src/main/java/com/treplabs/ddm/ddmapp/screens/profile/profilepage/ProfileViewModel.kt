package com.treplabs.ddm.ddmapp.screens.profile.profilepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.models.request.User
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val authRepository: FirebaseAuthRepository) : BaseViewModel() {

    private val _user = MutableLiveData<User>()

    val user: LiveData<User>
        get() = _user


    override fun addAllLiveDataToObservablesList() {
    }

    fun reloadUser() {
        Timber.d("Got new user ${authRepository.getUser()}")
        _user.value = authRepository.getUser()
    }
}

package com.treplabs.ddm.ddmapp.screens.profile.profilepage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.models.request.User
import javax.inject.Inject

class ProfileViewModel @Inject constructor(prefsValueHelper: PrefsValueHelper) : BaseViewModel() {

    private val _user = MutableLiveData<User>(prefsValueHelper.getUser())

    val user: LiveData<User>
        get() = _user


    override fun addAllLiveDataToObservablesList() {
    }
}

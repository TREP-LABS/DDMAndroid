package com.treplabs.ddm.ddmapp.screens.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(private val prefsValueHelper: PrefsValueHelper) : BaseViewModel() {

    private val _user = MutableLiveData<FirebaseUser>(FirebaseAuth.getInstance().currentUser!!)

    val user: LiveData<FirebaseUser>
        get() = _user

    override fun addAllLiveDataToObservablesList() {
    }
}

package com.treplabs.ddm.ddmapp.screens.diagnosisresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import javax.inject.Inject

class DiagnoseResultViewModel @Inject constructor() : BaseViewModel() {

    private val _user = MutableLiveData<FirebaseUser>(FirebaseAuth.getInstance().currentUser)

    val user: LiveData<FirebaseUser>
        get() = _user

    override fun addAllLiveDataToObservablesList() {
    }

}


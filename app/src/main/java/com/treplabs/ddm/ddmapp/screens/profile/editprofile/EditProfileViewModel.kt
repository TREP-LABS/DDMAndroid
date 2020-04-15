package com.treplabs.ddm.ddmapp.screens.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.extensions.firstName
import com.treplabs.ddm.extensions.lastName
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(private val prefsValueHelper: PrefsValueHelper) : BaseViewModel() {

    private val _user = MutableLiveData<User>(getUser())

    val user: LiveData<User>
        get() = _user

    var updatedProfileImageUrl: String? = null

    fun updateUserProfileUrl(url: String) {

    }

    fun updateUserInfo (firstName: String, lastName: String, phoneNumber: String) {

    }


    private fun getUser(): User {
        var user = prefsValueHelper.getUser()
        if (user == null) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser!!
            user = User(firebaseUser.uid, firebaseUser.firstName(), firebaseUser.lastName(),
                "", firebaseUser.email, firebaseUser.photoUrl.toString())
        }
        return user
    }


    override fun addAllLiveDataToObservablesList() {
    }
}

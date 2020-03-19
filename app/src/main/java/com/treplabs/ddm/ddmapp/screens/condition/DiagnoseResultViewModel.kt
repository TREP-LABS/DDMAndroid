package com.treplabs.ddm.ddmapp.screens.condition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.treplabs.ddm.base.BaseViewModel
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.datasources.repositories.FirebaseAuthRepository
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.networkutils.LoadingStatus
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.disposeBy
import com.treplabs.ddm.utils.Event
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class DiagnoseResultViewModel @Inject constructor() : BaseViewModel() {

    private val _user = MutableLiveData<FirebaseUser>(FirebaseAuth.getInstance().currentUser)

    val user: LiveData<FirebaseUser>
        get() = _user

    override fun addAllLiveDataToObservablesList() {
    }

}


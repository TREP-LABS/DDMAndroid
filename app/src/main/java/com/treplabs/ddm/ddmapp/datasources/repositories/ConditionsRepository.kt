package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.networkutils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ConditionsRepository @Inject constructor() {

    fun signInWithGoogle(acct: GoogleSignInAccount): Single<Result<FirebaseUser>> {
        return FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))
            .toSingle().toFirebaseUserResult()
    }

    fun getCondition(key: String): Single<Result<Condition>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.conditions).document(key).get()
            .toSingle()
            .map { Result.Success(it.toObject(Condition::class.java)!!) as Result<Condition> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.networkutils.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by Rasheed Sulayman.
 */

//TODO reuse code. Abstract calls to .onErrorReturn, .observeOn to a util method
class FirebaseAuthRepository @Inject constructor() {

    fun setUserDisplayName(displayName: String): Single<Result<FirebaseUser>> {
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
        val user = FirebaseAuth.getInstance().currentUser!!
        return user.updateProfile(profileUpdates).toFirebaseUser(user)
    }

    fun signInWithGoogle(acct: GoogleSignInAccount): Single<Result<FirebaseUser>> {
        return FirebaseAuth.getInstance().signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken, null))
            .toSingle().toFirebaseUserResult()
    }

    fun signInWithPassword(signRequest: SignInRequest): Single<Result<FirebaseUser>> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(signRequest.email, signRequest.password)
            .toSingle().toFirebaseUserResult()
    }

    fun signUpWithPassWord(signUpRequest: SignUpRequest): Single<Result<FirebaseUser>> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(signUpRequest.email, signUpRequest.password)
            .toSingle().toFirebaseUserResult()
    }

    fun saveUserInfo(user: User): Single<Result<Void>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.users).document(user.firebaseUid!!).set(user)
            .toSingle().map { Result.Success(it) as Result<Void> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }

    /*fun createUserInfo(user: User): Single<Result<Void>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.users).document(user.firebaseUid!!).set(user)
            .toSingle().map { Result.Success(it) as Result<Void> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }*/
}

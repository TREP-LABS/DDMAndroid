package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.networkutils.toSingle
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.toFirebaseUserResult
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Rasheed Sulayman.
 */
class FirebaseAuthRepository @Inject constructor() {

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
}
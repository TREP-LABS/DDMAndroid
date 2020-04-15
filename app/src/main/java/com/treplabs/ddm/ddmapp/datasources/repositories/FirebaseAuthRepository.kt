package com.treplabs.ddm.ddmapp.datasources.repositories

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.treplabs.ddm.Constants
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.models.request.SignInRequest
import com.treplabs.ddm.ddmapp.models.request.SignUpRequest
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.ddmapp.models.request.User
import com.treplabs.ddm.extensions.firstName
import com.treplabs.ddm.extensions.lastName
import com.treplabs.ddm.networkutils.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.File
import javax.inject.Inject

/**
 * Created by Rasheed Sulayman.
 */

//TODO reuse code. Abstract calls to .onErrorReturn, .observeOn to a util method
class FirebaseAuthRepository @Inject constructor(private val prefsValueHelper: PrefsValueHelper) {

    fun setUserDisplayName(displayName: String): Single<Result<Void>> {
        val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
        val user = FirebaseAuth.getInstance().currentUser!!
        return user.updateProfile(profileUpdates).toResult()
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
        return Firebase.firestore.collection(Constants.FireStorePaths.USERS).document(user.firebaseUid!!).set(user)
            .toSingle().map { Result.Success(it) as Result<Void> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun uploadFile(file: File, imageRefs: StorageReference) : Single<Result<Uri>> {
        val fileUri = Uri.fromFile(File(file.absolutePath))
        return imageRefs.putFile(fileUri).continueWithTask { task ->
            if (!task.isSuccessful) { task.exception?.let { throw it } }
            imageRefs.downloadUrl
        }.toSingle().map { Result.Success(it) as Result<Uri> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getUser(): User {
        var user = prefsValueHelper.getUser()
        if (user == null) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser!!
            user = User(
                firebaseUser.uid, firebaseUser.firstName(), firebaseUser.lastName(),
                "", firebaseUser.email, firebaseUser.photoUrl.toString()
            )
        }
        return user
    }

    fun UploadTask.toSingle() {

    }
}

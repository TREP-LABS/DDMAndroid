package com.treplabs.ddm.networkutils

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import com.treplabs.ddm.ddmapp.models.request.Condition
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher
import retrofit2.Response
import timber.log.Timber

/**
 * Created by Rasheed Sulayman.
 */

fun <T : AuthResult> Single<T>.toFirebaseUserResult(): Single<Result<FirebaseUser>> {
    return compose { item ->
        item
            .map { getFirebaseUserResult(it) }
            .doOnError { e -> Timber.e(e) }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }
}


fun defaultErrorHandler(): (Throwable) -> Result.Error =
    { e -> Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE) }


fun getFirebaseUserResult(authResult: AuthResult): Result<FirebaseUser> = Result.Success(authResult.user!!)

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) = apply { compositeDisposable.add(this) }

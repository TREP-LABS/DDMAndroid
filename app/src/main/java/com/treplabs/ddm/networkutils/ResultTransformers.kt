package com.treplabs.ddm.networkutils

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.reactivestreams.Publisher
import retrofit2.Response
import timber.log.Timber

// Still not sure of what to name this file as of now 🤔

fun <T : Any> Single<Response<BaseAPIResponse<T>>>.toResult(): Single<Result<T>> {
    return compose { item ->
        item
            .map { getAPIResult(it) }
            .doOnError { e -> Timber.e(e) }
            .onErrorReturn { e -> Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T : AuthResult> Single<T>.toFirebaseUserResult(): Single<Result<FirebaseUser>> {
    return compose { item ->
        item
            .map { getFirebaseUserResult(it) }
            .doOnError { e -> Timber.e(e) }
            .onErrorReturn { e -> Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun getFirebaseUserResult(authResult: AuthResult): Result<FirebaseUser> = Result.Success(authResult.user!!)

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

fun Disposable.disposeBy(compositeDisposable: CompositeDisposable) = apply { compositeDisposable.add(this) }

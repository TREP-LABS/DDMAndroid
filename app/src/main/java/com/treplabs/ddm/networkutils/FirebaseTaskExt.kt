package com.treplabs.ddm.networkutils

import androidx.annotation.CheckResult
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Single

@CheckResult
fun <R> Task<R>.toSingle() = Single.create<R> { emitter ->
    addOnSuccessListener { emitter.onSuccess(it) }
        .addOnFailureListener { emitter.onError(it) }
}

@CheckResult
fun <R> Task<R>.toCompletable() = Completable.create { emitter ->
    addOnSuccessListener { emitter.onComplete() }
        .addOnFailureListener { emitter.onError(it) }
}

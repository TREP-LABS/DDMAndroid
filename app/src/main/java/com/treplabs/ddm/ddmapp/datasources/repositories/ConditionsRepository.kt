package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants
import com.treplabs.ddm.ddmapp.datasources.FilterableDataSource
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.networkutils.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class ConditionsRepository @Inject constructor() : FilterableDataSource<Condition> {

   private fun getConditions(queryText: String): List<Condition> {
        return try {
            val task = Firebase.firestore.collection(Constants.FireStorePaths.conditions)
                .whereGreaterThanOrEqualTo(Constants.APIDataKeys.NAME, queryText)
                .whereLessThanOrEqualTo(Constants.APIDataKeys.NAME, queryText + '\uf8ff').get()
            Tasks.await(task).toObjects(Condition::class.java)
        } catch (e: ExecutionException) {
            emptyList()
        }
    }

    override fun getFilteredItems(queryText: String): List<Condition> = getConditions(queryText)

    fun getCondition(key: String): Single<Result<Condition>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.conditions).document(key).get()
            .toSingle()
            .map { Result.Success(it.toObject(Condition::class.java)!!) as Result<Condition> }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

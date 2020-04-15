package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants
import com.treplabs.ddm.ddmapp.datasources.FilterableDataSource
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.defaultErrorHandler
import com.treplabs.ddm.networkutils.toSingle
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class ConditionsRepository @Inject constructor() : FilterableDataSource<Condition> {

    private fun getConditions(queryText: String): List<Condition> {
        return try {
            val task = Firebase.firestore.collection(Constants.FireStorePaths.CONDITIONS)
                .whereGreaterThanOrEqualTo(Constants.APIDataKeys.KEY, queryText)
                .whereLessThanOrEqualTo(Constants.APIDataKeys.KEY, queryText + '\uf8ff').get()
            Tasks.await(task).toObjects(Condition::class.java).also {
                Timber.d("Got the stuff: $it")
            }
        } catch (e: ExecutionException) {
            Timber.d("We have an exception")
            Timber.e(e)
            emptyList()
        }
    }

     fun getAllConditions(): Single<Result<List<Condition>>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.CONDITIONS).get()
            .toSingle().map {
                Result.Success(it.toObjects(Condition::class.java)) as Result<List<Condition>>
            }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun getFilteredItems(queryText: String): List<Condition> = getConditions(queryText)

    fun getCondition(key: String): Single<Result<Condition>> {
        return Firebase.firestore.collection(Constants.FireStorePaths.CONDITIONS).document(key).get()
            .toSingle()
            .map {
                Timber.d("Inside map  docment sanpshot ${it}")
                Result.Success(it.toObject(Condition::class.java)!!) as Result<Condition>
            }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

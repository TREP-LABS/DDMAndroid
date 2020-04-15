package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants
import com.treplabs.ddm.Constants.APIDataKeys
import com.treplabs.ddm.Constants.FireStorePaths
import com.treplabs.ddm.ddmapp.datasources.FilterableDataSource
import com.treplabs.ddm.ddmapp.models.request.Condition
import com.treplabs.ddm.ddmapp.models.request.Symptom
import com.treplabs.ddm.networkutils.Result
import com.treplabs.ddm.networkutils.defaultErrorHandler
import com.treplabs.ddm.networkutils.toSingle
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class SymptomsRepository @Inject constructor() : FilterableDataSource<Symptom> {

    private fun getSymptoms(queryText: String): List<Symptom> {
        return try {
            val task = Firebase.firestore.collection(FireStorePaths.SYMPTOMS)
                .whereGreaterThanOrEqualTo(APIDataKeys.KEY, queryText)
               .whereLessThanOrEqualTo(APIDataKeys.KEY, queryText + '\uf8ff').get()
            Tasks.await(task).toObjects(Symptom::class.java).also {
                Timber.d("Got the stuff: $it"  )
            }
        } catch (e: ExecutionException) {
            Timber.d("We have an exception")
            Timber.e(e)
            emptyList()
        }
    }

    fun getAllSymptoms(): Single<Result<List<Symptom>>> {
        return Firebase.firestore.collection(FireStorePaths.SYMPTOMS).get()
            .toSingle().map {
                Result.Success(it.toObjects(Symptom::class.java)) as Result<List<Symptom>>
            }
            .onErrorReturn(defaultErrorHandler())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFilteredItems(queryText: String): List<Symptom> = getSymptoms(queryText)

}

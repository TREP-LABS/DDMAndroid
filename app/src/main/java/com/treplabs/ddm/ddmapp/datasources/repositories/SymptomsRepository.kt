package com.treplabs.ddm.ddmapp.datasources.repositories

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.treplabs.ddm.Constants.APIDataKeys
import com.treplabs.ddm.Constants.FireStorePaths
import com.treplabs.ddm.ddmapp.models.request.Symptom
import java.util.concurrent.ExecutionException
import javax.inject.Inject

class SymptomsRepository @Inject constructor() {

    fun getSymptoms(queryText: String): List<Symptom> {
        return try {
            val task = Firebase.firestore.collection(FireStorePaths.symptoms)
                .whereGreaterThanOrEqualTo(APIDataKeys.NAME, queryText)
                .whereLessThanOrEqualTo(APIDataKeys.NAME, queryText + '\uf8ff').get()
            Tasks.await(task).toObjects(Symptom::class.java)
        } catch (e: ExecutionException) {
            emptyList()
        }
    }
}
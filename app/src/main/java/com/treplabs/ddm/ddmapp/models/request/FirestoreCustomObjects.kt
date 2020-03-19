package com.treplabs.ddm.ddmapp.models.request

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize


data class Symptom(
    val name: String,
    val description: String,
    @PropertyName("conditions")
    val conditionsKeys: List<String>
) {
    override fun toString(): String = name
}

@Parcelize
data class Condition(
    val name: String,
    val description: String,
    @PropertyName("scientific_name")
    val scientificName: String
): Parcelable

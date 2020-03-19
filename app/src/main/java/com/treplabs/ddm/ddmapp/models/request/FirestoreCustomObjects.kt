package com.treplabs.ddm.ddmapp.models.request

import com.google.firebase.firestore.PropertyName


data class Symptom(
    val name: String,
    val description: String,
    @PropertyName("conditions")
    val conditionsKeys: List<String>
) {
    override fun toString(): String = name
}

data class Condition(
    val name: String,
    val description: String,
    @PropertyName("scientific_name")
    val scientificName: String
)

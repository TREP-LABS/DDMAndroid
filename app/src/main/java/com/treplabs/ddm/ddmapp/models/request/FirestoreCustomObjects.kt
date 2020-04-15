package com.treplabs.ddm.ddmapp.models.request

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import com.treplabs.ddm.ddmapp.FilterableDataClass
import kotlinx.android.parcel.Parcelize


data class Symptom(
    var name: String?,
    var description: String?,
    var key: String?,

    var conditionKeys: List<String>?
) : FilterableDataClass {

    override fun getFilterKey() = name!!

    constructor() : this(null, null, null, null)

    override fun toString(): String = name!!
}

@Parcelize
data class Condition(
    var name: String?,
    var description: String?,
    var scientificName: String?
) : Parcelable, FilterableDataClass {

    constructor() : this(null, null, null)

    override fun toString(): String = name!!
    override fun getFilterKey() = name!!
}

@Parcelize
data class User(
    var firebaseUid: String?,
    var firstName: String?,
    var lastName: String?,
    var phoneNumber: String?,
    var email: String?,
    var profileImageUrl: String?

) : Parcelable {
    constructor() : this(null, null, null, null, null, null)

    fun fullName() = "$firstName $lastName"
}

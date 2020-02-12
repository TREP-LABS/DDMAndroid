package com.treplabs.ddm.networkutils

import com.google.gson.annotations.SerializedName

/**
 * Created by Rasheed Sulayman.
 */
class BaseAPIResponse<T> {

    @SerializedName("status")
    lateinit var status: String

    @SerializedName("data")
    var data: T? = null
}

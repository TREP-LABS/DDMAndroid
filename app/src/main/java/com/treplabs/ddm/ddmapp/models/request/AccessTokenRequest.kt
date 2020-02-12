package com.treplabs.ddm.ddmapp.models.request

import com.google.gson.annotations.SerializedName

/**
 * Created by Rasheed Sulayman.
 */
data class AccessTokenRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String
)

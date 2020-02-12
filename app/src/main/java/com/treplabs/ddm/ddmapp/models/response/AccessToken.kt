package com.treplabs.ddm.ddmapp.models.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Rasheed Sulayman.
 */
data class AccessToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)

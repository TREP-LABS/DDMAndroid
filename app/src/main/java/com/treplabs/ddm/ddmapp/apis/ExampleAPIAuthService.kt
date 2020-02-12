package com.treplabs.ddm.ddmapp.apis

import com.treplabs.ddm.networkutils.BaseAPIResponse
import com.treplabs.ddm.ddmapp.models.request.AccessTokenRequest
import com.treplabs.ddm.ddmapp.models.response.AccessToken
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Rasheed Sulayman.
 */
interface ExampleAPIAuthService {

    @POST("/oauth/token")
    fun getAccessToken(@Body body: AccessTokenRequest): Call<BaseAPIResponse<AccessToken>>
}

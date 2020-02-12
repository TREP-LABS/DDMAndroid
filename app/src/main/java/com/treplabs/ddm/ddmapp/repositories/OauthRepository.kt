package com.treplabs.ddm.ddmapp.repositories

import com.treplabs.ddm.BuildConfig
import com.treplabs.ddm.ddmapp.apis.ExampleAPIAuthService
import com.treplabs.ddm.ddmapp.models.request.AccessTokenRequest
import com.treplabs.ddm.ddmapp.models.response.AccessToken
import com.treplabs.ddm.networkutils.GENERIC_ERROR_CODE
import com.treplabs.ddm.networkutils.GENERIC_ERROR_MESSAGE
import com.treplabs.ddm.networkutils.getAPIResult
import com.treplabs.ddm.networkutils.Result

import javax.inject.Inject

/**
 * Created by Rasheed Sulayman.
 */
class OauthRepository @Inject constructor(private val oauthService: ExampleAPIAuthService) {

    fun getAccessToken(): Result<AccessToken> {
        return try {
            getAPIResult(oauthService.getAccessToken(getTokenRequest()).execute())
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, e.message ?: GENERIC_ERROR_MESSAGE)
        }
    }

    private fun getTokenRequest() = AccessTokenRequest(
        BuildConfig.OAUTH_GRANT_TYPE, BuildConfig.OAUTH_CLIENT_ID, BuildConfig.OAUTH_CLIENT_SECRET
    )
}

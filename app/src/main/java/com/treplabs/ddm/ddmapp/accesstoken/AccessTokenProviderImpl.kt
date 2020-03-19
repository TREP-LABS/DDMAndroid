package com.treplabs.ddm.ddmapp.accesstoken

import com.treplabs.ddm.auth.AccessTokenProvider
import com.treplabs.ddm.ddmapp.PrefsValueHelper
import com.treplabs.ddm.ddmapp.datasources.repositories.OauthRepository
import javax.inject.Inject
import javax.inject.Singleton
import com.treplabs.ddm.networkutils.Result

/**
 * Created by Rasheed Sulayman.
 */
@Singleton
class AccessTokenProviderImpl @Inject constructor(
    private val oauthRepository: OauthRepository,
    private val prefsValueHelper: PrefsValueHelper
) : AccessTokenProvider {

    override fun token(): String? = prefsValueHelper.getAccessToken()

    override fun refreshToken(): String? {
        return when (val result = oauthRepository.getAccessToken()) {
            is Result.Success -> {
                prefsValueHelper.setAccessToken(result.data.accessToken)
                result.data.accessToken
            }
            is Result.Error -> null
        }
    }
}

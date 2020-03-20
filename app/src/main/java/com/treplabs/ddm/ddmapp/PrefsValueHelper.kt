package com.treplabs.ddm.ddmapp

import com.treplabs.ddm.utils.PrefsUtils
import javax.inject.Inject

/**
 * Created by Rasheed Sulayman.
 */
class PrefsValueHelper @Inject constructor(private val prefsUtils: PrefsUtils) {

    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val USER_DATA = "USER_DATA"
        const val AUTH_FLOW_PHONE_NUMBER = "AUTH_FLOW_PHONE_NUMBER"
        const val VERIFIED_PHONE_NUMBER = "VERIFIED_PHONE_NUMBER"
        const val DEMO_SHOWN = "DEMO_SHOWN"
        const val EMAIL = "EMAIL"
    }

    fun setLastSignedInEmail(accessToken: String) = prefsUtils.putString(EMAIL, accessToken)

    fun getLastSignedInEmail() = prefsUtils.getString(EMAIL, null)


    fun setAccessToken(accessToken: String) = prefsUtils.putString(ACCESS_TOKEN, accessToken)

    fun getAccessToken() = prefsUtils.getString(ACCESS_TOKEN, null)

    fun setDemoShownStatus(isDemoShown: Boolean) {
        prefsUtils.putBoolean(DEMO_SHOWN, isDemoShown)
    }

    fun getIsDemoShown() = prefsUtils.getBoolean(DEMO_SHOWN, false)
    fun setIsDemoShown() = prefsUtils.putBoolean(DEMO_SHOWN, false)

    fun setLastVerifiedPhoneNumber(number: String) = prefsUtils.putString(VERIFIED_PHONE_NUMBER, number)

    fun getLastVerifiedPhoneNumber() = prefsUtils.getString(VERIFIED_PHONE_NUMBER, null)


}
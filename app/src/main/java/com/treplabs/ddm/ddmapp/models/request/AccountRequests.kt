package com.treplabs.ddm.ddmapp.models.request

data class SignInRequest(val email: String, val password: String)
typealias SignUpRequest = SignInRequest
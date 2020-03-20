package com.treplabs.ddm.ddmapp.models.request

data class SignUpRequest(val email: String, val password: String, val displayName: String)
data class SignInRequest(val email: String, val password: String)


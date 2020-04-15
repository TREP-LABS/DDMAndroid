package com.treplabs.ddm.extensions

import com.google.firebase.auth.FirebaseUser
import java.lang.StringBuilder


fun String.snakeCase(): String {
    val sb = StringBuilder()
    forEach { c ->
        if (c == ' ') sb.append('_') else sb.append(c.toLowerCase())
    }
    return sb.toString()
}


fun FirebaseUser.firstName(): String? {
    return displayName?.split(" ")?.firstOrNull()
}

fun FirebaseUser.lastName(): String? {
    return displayName?.split(" ")?.lastOrNull()
}
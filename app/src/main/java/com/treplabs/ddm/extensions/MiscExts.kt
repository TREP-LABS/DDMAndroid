package com.treplabs.ddm.extensions

import java.lang.StringBuilder


fun String.snakeCase(): String {
    val sb = StringBuilder()
    forEach { c ->
        if (c == ' ') sb.append('_') else sb.append(c.toLowerCase())
    }
    return sb.toString()
}


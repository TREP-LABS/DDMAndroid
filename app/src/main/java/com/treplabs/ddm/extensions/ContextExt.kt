package com.treplabs.ddm.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat
import com.treplabs.ddm.Constants.Misc.DEFAULT_FILENAME
import com.treplabs.ddm.Constants.Misc.DEFAULT_PHOTO_EXTENSION
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Rasheed Sulayman.
 */

fun Context.hasPermissions(permissionList: Array<String>): Boolean {
    for (permission in permissionList) {
        if (!hasPermission(permission)) return false
    }
    return true
}

fun Context.hasPermission(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

inline fun <reified T> Context.systemService(serviceName: String): T = getSystemService(serviceName) as T

@Throws(IOException::class)
fun Context.createImageFile(): File = createFileInFolder(getBaseStorageDirectory())

fun Context.getBaseStorageDirectory() =
    File(getExternalFilesDir(null), "GingerApp").apply { if (!exists()) mkdir() }

fun createFileInFolder(
    baseDirectory: File
): File = File(
    baseDirectory, SimpleDateFormat(DEFAULT_FILENAME, Locale.US)
        .format(System.currentTimeMillis()) + DEFAULT_PHOTO_EXTENSION
)

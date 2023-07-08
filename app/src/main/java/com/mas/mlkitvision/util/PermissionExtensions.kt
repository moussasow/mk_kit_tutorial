package com.mas.mlkitvision.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

fun Context.isPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

inline fun Context.cameraPermissionRequest(crossinline action: () -> Unit) {
    AlertDialog.Builder(this)
        .setTitle("Camera permission required")
        .setMessage("This app needs camera permission to work")
        .setPositiveButton("Allow Camera") { _, _ ->
            action.invoke()
        }
        .setNegativeButton("Cancel") { _, _ ->

        }.show()
}

fun Context.openCameraPermissionSettings() {
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS).also {
        val uri: Uri = Uri.fromParts("package", packageName, null)
        it.data = uri
        startActivity(it)
    }
}
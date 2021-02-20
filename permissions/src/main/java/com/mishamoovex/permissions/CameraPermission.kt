package com.mishamoovex.permissions

import android.Manifest
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

private val cameraPermissions = mutableListOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_EXTERNAL_STORAGE,
).also {

    if (isVersionAboveQ()) {
        it += Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

}.toTypedArray()

suspend fun FragmentActivity.awaitForCameraPermission(
    appId: String,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit) = { openAppSettingsActivity(appId) }
): Boolean = awaitForPermissions(cameraPermissions).also { approved ->
    if (!approved) {
        showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}

suspend fun FragmentActivity.awaitForCameraPermission(): Boolean = awaitForPermissions(cameraPermissions)

suspend fun Fragment.awaitForCameraPermissions(
    appId: String,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit) = { context?.openAppSettingsActivity(appId) }
): Boolean = awaitForPermissions(cameraPermissions).also { approved ->
    if (!approved) {
        context?.showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}

suspend fun Fragment.awaitForCameraPermissions(): Boolean = awaitForPermissions(cameraPermissions)
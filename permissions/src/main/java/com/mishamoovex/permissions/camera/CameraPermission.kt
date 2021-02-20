package com.mishamoovex.permissions.camera

import android.Manifest
import androidx.activity.result.ActivityResultRegistry
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mishamoovex.permissions.awaitForPermissions
import com.mishamoovex.permissions.isVersionAboveQ
import com.mishamoovex.permissions.openAppSettingsActivity
import com.mishamoovex.permissions.showPermissionRationaleDialog

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
    btnAction: (() -> Unit) = { openAppSettingsActivity(appId) },
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = awaitForPermissions(cameraPermissions,registry).also { approved ->
    if (!approved) {
        showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}

suspend fun FragmentActivity.awaitForCameraPermission(
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = awaitForPermissions(cameraPermissions,registry)

suspend fun Fragment.awaitForCameraPermissions(
    appId: String,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit) = { context?.openAppSettingsActivity(appId) },
    registry: ActivityResultRegistry = requireActivity().activityResultRegistry
): Boolean = awaitForPermissions(cameraPermissions,registry).also { approved ->
    if (!approved) {
        context?.showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}

suspend fun Fragment.awaitForCameraPermissions(
    registry: ActivityResultRegistry = requireActivity().activityResultRegistry
): Boolean = awaitForPermissions(cameraPermissions,registry)
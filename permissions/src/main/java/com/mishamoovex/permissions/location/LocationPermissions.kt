package com.mishamoovex.permissions.location

import android.Manifest
import androidx.activity.result.ActivityResultRegistry
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mishamoovex.permissions.*

private val requiredPermissions = { type: LocationPermissionType ->

    when (type) {
        LocationPermissionType.COARSE -> mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        LocationPermissionType.FINE -> mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
        .also {
            if (isVersionAboveQ()) {
                it += Manifest.permission.ACCESS_BACKGROUND_LOCATION
            }
        }
        .toTypedArray()
}

suspend fun FragmentActivity.awaitForLocationPermissions(
    type: LocationPermissionType,
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = awaitForPermissions(requiredPermissions(type), registry)

suspend fun FragmentActivity.awaitForLocationPermissions(
    type: LocationPermissionType,
    appId: String,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit) = { openAppSettingsActivity(appId) },
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = awaitForPermissions(requiredPermissions(type), registry).also { approved ->
    if (!approved) {
        showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}

suspend fun Fragment.awaitForLocationPermissions(
    type: LocationPermissionType,
    registry: ActivityResultRegistry = requireActivity().activityResultRegistry
): Boolean = awaitForPermissions(requiredPermissions(type), registry)

suspend fun Fragment.awaitForLocationPermissions(
    type: LocationPermissionType,
    appId: String,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit) = { context?.openAppSettingsActivity(appId) },
    registry: ActivityResultRegistry = requireActivity().activityResultRegistry
): Boolean = awaitForPermissions(requiredPermissions(type), registry).also { approved ->
    if (!approved) {
        context?.showPermissionRationaleDialog(title, message, btnTitle, icon, btnAction)
    }
}


package com.mishamoovex.permissions

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat

fun Context.showPermissionRationaleDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes btnTitle: Int,
    @DrawableRes icon: Int,
    btnAction: (() -> Unit)? = null
) {
    AlertDialog.Builder(this)
        .setTitle(getString(title))
        .setMessage(getString(message))
        .setIcon(getDrawableCompat(icon))
        .setPositiveButton(getString(btnTitle)) { dialog, _ ->
            btnAction?.invoke()
            dialog.dismiss()
        }
        .show()
}

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable =
    ResourcesCompat.getDrawable(resources, id, theme)!!

fun Context.openAppSettingsActivity(appId: String) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:$appId")
    startActivity(intent)
}
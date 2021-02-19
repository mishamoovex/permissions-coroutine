package com.mishamoovex.permissions

import android.os.Build

fun isVersionAboveQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

fun isVersionBelowQ(): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q

fun isVersionAboveO (): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun isVersionAboveP () : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P

fun isVersionAboveN() : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

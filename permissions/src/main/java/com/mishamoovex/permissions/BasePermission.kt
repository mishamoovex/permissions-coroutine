package com.mishamoovex.permissions

import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Fragment.awaitForPermissions(
    permissions: Array<String>,
    registry: ActivityResultRegistry = requireActivity().activityResultRegistry
): Boolean = suspendCancellableCoroutine { cont ->

    val grantPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),registry) { resultMapping ->
        val allGranted = resultMapping.all { it.value == true }
        cont.resume(allGranted)
    }

    lifecycleScope.launchWhenCreated { grantPermissions.launch(permissions) }
    cont.invokeOnCancellation { grantPermissions.unregister() }
}

suspend fun FragmentActivity.awaitForPermissions(
    permissions: Array<String>,
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = suspendCancellableCoroutine { cont ->

    val grantPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),registry) { resultMapping ->
        val allGranted = resultMapping.all { it.value == true }
        cont.resume(allGranted)
    }

    //prevents calling this method before the Fragment was created
    lifecycleScope.launchWhenCreated { grantPermissions.launch(permissions) }
    cont.invokeOnCancellation { grantPermissions.unregister() }
}

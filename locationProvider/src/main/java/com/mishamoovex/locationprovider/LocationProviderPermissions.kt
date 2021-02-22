package com.mishamoovex.locationprovider

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FragmentActivity.awaitForLocationProviderPermissions(
    priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = suspendCancellableCoroutine { cont ->

    lifecycleScope.launchWhenCreated {
        val settingsCheckResult = checkLocationSettings(priority)
        when (settingsCheckResult) {

            Result.Granted -> cont.resume(true)

            is Result.NotGranted -> {
                val resolvable = settingsCheckResult.resolvable
                val granted = if (resolvable != null){
                    requestLocationProviderPermissions(resolvable, registry)
                }else{
                    false
                }
                cont.resume(granted)
            }
        }
    }
}

private suspend fun FragmentActivity.requestLocationProviderPermissions(
    resolvable: ResolvableApiException,
    registry: ActivityResultRegistry = activityResultRegistry
): Boolean = suspendCancellableCoroutine { cont ->

    val startForResult = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult(),registry) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            cont.resume(true)
        }else{
            cont.resume(false)
        }
    }

    val intentSenderRequest = IntentSenderRequest.Builder(resolvable.resolution).build()

    startForResult.launch(intentSenderRequest)
    cont.invokeOnCancellation { startForResult.unregister() }
}

private suspend fun Context.checkLocationSettings(
    priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY
): Result = suspendCancellableCoroutine { cont ->

    val locationRequest = LocationRequest.create().apply {
        this.priority = priority
    }

    val settingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .build()

    val settingsClient = LocationServices.getSettingsClient(this)
    val settingsTask = settingsClient.checkLocationSettings(settingsRequest)

    with(settingsTask) {
        addOnSuccessListener { cont.resume(Result.Granted) }
        addOnFailureListener { exception ->
            val result = Result.NotGranted(exception.getResolvable())
            cont.resume(result)
        }
    }


}

private fun Exception.getResolvable(): ResolvableApiException? {
    val statusCode = (this as? ApiException)?.statusCode
    // Location settings are not satisfied
    return if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
        this as? ResolvableApiException
    } else {
        null
    }
}

sealed class Result {
    object Granted : Result()
    data class NotGranted(val resolvable: ResolvableApiException?) : Result()
}
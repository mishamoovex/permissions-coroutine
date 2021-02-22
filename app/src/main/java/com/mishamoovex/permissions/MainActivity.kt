package com.mishamoovex.permissions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mishamoovex.locationprovider.awaitForLocationProviderPermissions
import com.mishamoovex.permissions.location.LocationPermissionType
import com.mishamoovex.permissions.location.awaitForLocationPermissions
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
//          val location =  awaitForLocationPermissions(LocationPermissionType.FINE)
//            println("Location permission:$location")

            val provider = awaitForLocationProviderPermissions()
            println("Location provider permission:$provider")
        }
    }
}
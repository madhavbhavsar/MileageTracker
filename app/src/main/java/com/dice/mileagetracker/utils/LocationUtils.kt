package com.dice.mileagetracker.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng


private const val REQUEST_LOCATION_PERMISSION = 1001

lateinit var locationCallback: LocationCallback

lateinit var locationProvider: FusedLocationProviderClient

@SuppressLint("MissingPermission")
@Composable
fun getUserLocation(context: Context): LatLng {

    locationProvider = LocationServices.getFusedLocationProviderClient(context)

    var currentUserLocation by remember { mutableStateOf(LatLng(0.0,0.0)) }

    DisposableEffect(key1 = locationProvider) {
        locationCallback = object : LocationCallback() {
            //1
            override fun onLocationResult(result: LocationResult) {

                for (location in result.locations) {
                    currentUserLocation = LatLng(location.latitude, location.longitude)
                    Log.d("LOCATION_TAG", "${location.latitude},${location.longitude}")
                }

                locationProvider.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            val lat = location.latitude
                            val long = location.longitude
                            // Update data class with location data
                            currentUserLocation = LatLng(lat.toDouble(),long.toDouble())
                        }
                    }
                    .addOnFailureListener {
                        Log.e("Location_error", "${it.message}")
                    }

            }
        }
        if (hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            /*locationUpdate()*/
        } else {
            askPermissions(
                context, REQUEST_LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
        //3
        onDispose {
            stopLocationUpdate()
        }
    }
    //4
    return currentUserLocation
}

fun stopLocationUpdate() {
    try {
        val removeTask = locationProvider.removeLocationUpdates(locationCallback)
        removeTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("LOCATION_TAG", "Location Callback removed.")
            } else {
                Log.d("LOCATION_TAG", "Failed to remove Location Callback.")
            }
        }
    } catch (se: SecurityException) {
        Log.e("LOCATION_TAG", "Failed to remove Location Callback.. $se")
    }
}

//check if permission is already granted
fun hasPermissions(context: Context, vararg permissions: String) =
    permissions.all { permission ->
        ActivityCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

//else ask permission
fun askPermissions(context: Context, requestCode: Int, vararg permissions: String) {
    ActivityCompat.requestPermissions(
        context as Activity,
        permissions,
        requestCode
    )
}



@RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun fetchUserLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    userLocated: (LatLng?) -> Unit = {},
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    userLocated.invoke(userLatLng)
                }
            }
        } catch (e: SecurityException) {
            Log.e(
                "LOCATION_TAG",
                "Permission for location access was revoked: ${e.localizedMessage}"
            )
            userLocated.invoke(null)
        }
    } else {
        Log.e("LOCATION_TAG", "Location permission is not granted.")
        userLocated.invoke(null)
    }
}

@Composable
fun LocationPermission(context: Context, onGranted: () -> Unit = {}) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted.invoke()
        } else {
            Log.e("LOCATION_TAG", "Location permission was denied by the user.")
        }
    }

    LaunchedEffect(Unit) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                onGranted.invoke()
            }

            else -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}
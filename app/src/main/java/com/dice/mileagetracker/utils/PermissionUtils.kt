package com.dice.mileagetracker.utils

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class LocationPermissionManager(
    private val context: Context,
    private val onPermissionGranted: () -> Unit,
    private val onSettingsRequired: () -> Unit
) {
    private var deniedCount = 0

    fun handlePermissionResult(permissions: Map<String, Boolean>) {
        val isFineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val isCoarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (isFineGranted) {
            onPermissionGranted()
        } else {
            deniedCount++
            if (deniedCount >= 3) {
                onSettingsRequired()
            }
        }
    }

    fun needsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    }

    fun permissionArray(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}


class NotificationPermissionManager(
    private val context: Context,
    private val onPermissionGranted: () -> Unit,
    private val onSettingsRequired: () -> Unit
) {
    private var deniedCount = 0

    fun handleResult(granted: Boolean) {
        if (granted) {
            onPermissionGranted()
        } else {
            deniedCount++
            if (deniedCount >= 2) {
                onSettingsRequired()
            }
        }
    }

    fun isNotificationPermissionNeeded(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
    }
}


fun showNotificationSettingsDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Notification Permission Required")
        .setMessage("Please enable notifications from settings to receive journey alerts.")
        .setPositiveButton("Go to Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
        }
        .setNegativeButton("Cancel", null)
        .show()
}

fun showLocationSettingsDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Location Permission Required")
        .setMessage("Please enable fine location permission from settings.")
        .setPositiveButton("Go to Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
        .setNegativeButton("Cancel", null)
        .show()
}

class BackgroundLocationPermissionManager(
    private val context: Context,
    private val onPermissionGranted: () -> Unit,
    private val onSettingsRequired: () -> Unit
) {
    private var deniedCount = 0

    fun handleResult(granted: Boolean) {
        if (granted) {
            onPermissionGranted()
        } else {
            deniedCount++
            if (deniedCount >= 2) {
                onSettingsRequired()
            }
        }
    }

    fun isNeeded(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
    }
}

fun showBackgroundLocationSettingsDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Background Location Required")
        .setMessage("To track your journey even in background, please allow background location in settings.")
        .setPositiveButton("Go to Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
        .setNegativeButton("Cancel", null)
        .show()
}


@Composable
fun CombinedPermissionHandler(
    onAllPermissionsGranted: () -> Unit
): (Context) -> Unit {
    val context = LocalContext.current

    // Step 3: Background location
    val backgroundManager = remember {
        BackgroundLocationPermissionManager(
            context = context,
            onPermissionGranted = onAllPermissionsGranted,
            onSettingsRequired = {
                showBackgroundLocationSettingsDialog(context)
            }
        )
    }

    val backgroundLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        backgroundManager.handleResult(granted)
    }

    // Step 2: Notification permission
    val notificationManager = remember {
        NotificationPermissionManager(
            context = context,
            onPermissionGranted = {
                if (backgroundManager.isNeeded()) {
                    backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    onAllPermissionsGranted()
                }
            },
            onSettingsRequired = {
                showNotificationSettingsDialog(context)
            }
        )
    }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        notificationManager.handleResult(granted)
    }

    // Step 1: Location permission
    val locationManager = remember {
        LocationPermissionManager(
            context = context,
            onPermissionGranted = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    notificationManager.isNotificationPermissionNeeded()
                ) {
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else if (backgroundManager.isNeeded()) {
                    backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    onAllPermissionsGranted()
                }
            },
            onSettingsRequired = {
                showLocationSettingsDialog(context)
            }
        )
    }

    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        locationManager.handlePermissionResult(permissions)
    }

    return remember {
        {
            if (locationManager.needsPermission()) {
                locationLauncher.launch(locationManager.permissionArray())
            } else {
                // Already has fine location
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    notificationManager.isNotificationPermissionNeeded()
                ) {
                    notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else if (backgroundManager.isNeeded()) {
                    backgroundLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    onAllPermissionsGranted()
                }
            }
        }
    }
}
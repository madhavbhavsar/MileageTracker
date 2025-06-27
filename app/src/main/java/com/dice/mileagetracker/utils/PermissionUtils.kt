package com.dice.mileagetracker.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

fun isLocationEnabled(context: Context): Boolean {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

@Composable
fun PermissionHandlerFlow(
    start: Boolean,
    onSuccess: () -> Unit,
    onComplete: () -> Unit
) {
    if (!start) return

    val context = LocalContext.current
    val activity = context as Activity

    val permissions = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
        Manifest.permission.POST_NOTIFICATIONS
    )

    var currentIndex by remember { mutableStateOf(0) }
    var showRationale by remember { mutableStateOf(false) }
    var rationaleText by remember { mutableStateOf("") }
    var permissionToRequest by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            currentIndex++
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            onComplete()
        }
    }

    LaunchedEffect(currentIndex) {
        if (currentIndex >= permissions.size) {
            // ✅ All permissions granted — now check GPS
            if (!isLocationEnabled(context)) {
                Toast.makeText(context, "Please enable GPS to start journey", Toast.LENGTH_LONG).show()
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                onComplete()
                return@LaunchedEffect
            }

            // 🚀 All good: permissions + GPS
            onSuccess()
            onComplete()
        } else {
            val perm = permissions[currentIndex]
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, perm)) {
                rationaleText = "This permission is required for ${perm.split('.').last().replace("_", " ").lowercase()}."
                permissionToRequest = perm
                showRationale = true
            } else {
                launcher.launch(perm)
            }
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text("Permission Required") },
            text = { Text(rationaleText) },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    launcher.launch(permissionToRequest)
                }) { Text("Allow") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationale = false
                    onComplete()
                }) { Text("Deny") }
            }
        )
    }
}
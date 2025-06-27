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
import com.dice.mileagetracker.utils.Constants.rationalText

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
    var rationaleText by remember { mutableStateOf(Constants.EMPTY) }
    var permissionToRequest by remember { mutableStateOf(Constants.EMPTY) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            currentIndex++
        } else {
            Toast.makeText(context, Constants.PERMISSION_DENIED, Toast.LENGTH_SHORT).show()
            onComplete()
        }
    }

    LaunchedEffect(currentIndex) {
        if (currentIndex >= permissions.size) {
            // ✅ All permissions granted — now check GPS
            if (!isLocationEnabled(context)) {
                Toast.makeText(context, Constants.ENABLE_GPS, Toast.LENGTH_LONG).show()
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
                rationaleText = rationalText(perm)
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
            title = { Text(Constants.PERMISSION_REQUIRED) },
            text = { Text(rationaleText) },
            confirmButton = {
                TextButton(onClick = {
                    showRationale = false
                    launcher.launch(permissionToRequest)
                }) { Text(Constants.ALLOW) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showRationale = false
                    onComplete()
                }) { Text(Constants.DENY) }
            }
        )
    }
}
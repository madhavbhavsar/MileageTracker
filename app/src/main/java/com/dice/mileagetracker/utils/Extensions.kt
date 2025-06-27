package com.dice.mileagetracker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dice.mileagetracker.utils.Constants.geoLocation
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
fun Modifier.singleClickable(
    onPressedChange: (Boolean) -> Unit = {},
    onClick: () -> Unit
): Modifier {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    // Notify the external lambda about the isPressed state changes
    LaunchedEffect(isPressed) {
        onPressedChange(isPressed)
    }
    return this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = {
            ClickHelper.getInstance().clickOnce {
                onClick()
            }
        }
    )
}
@Composable
fun Modifier.simpleClickable(
    onPressedChange: (Boolean) -> Unit = {},
    onClick: () -> Unit
): Modifier {
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    // Notify the external lambda about the isPressed state changes
    LaunchedEffect(isPressed) {
        onPressedChange(isPressed)
    }
    return this.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = onClick
    )
}

fun Context.openGoogleMaps(originLat: Float?, originLng: Float?, destLat: Float?, destLng: Float?) {
    try {
        if (originLat == null || originLng == null || destLat == null || destLng == null) return
        val uri = Uri.parse(
            geoLocation(originLat, originLng, destLat, destLng)
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(Constants.GOOGLE_MAPS)
        startActivity(intent)
    } catch (_: Exception) {
        Toast.makeText(this, Constants.UNAVAILABLE, Toast.LENGTH_SHORT).show()
    }
}

fun formatElapsedTime(seconds: Long): String {
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format("%02dm:%02ds", mins, secs)
}

fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371.0 // Earth radius in km

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)

    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return R * c
}
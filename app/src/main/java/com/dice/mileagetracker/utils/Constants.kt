package com.dice.mileagetracker.utils

import com.dice.mileagetracker.BuildConfig

object PrefKey {
    const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID
    const val JOURNEY_ID = "journeyId"
    const val IS_TRACKING_PAUSED = "isTrackingPaused"
    const val IS_TRACKING = "isTracking"
    const val TRACKING_START_TIME = "trackingStartTime"
}

object Notification {
    const val CHANNEL_ID = "location"
    const val CHANNEL_NAME = "Location"
}

object Constants {
    const val EMPTY = ""
    const val SPACE = " "
    const val SPLASH = "Splash Screen"
    const val UNAVAILABLE = "Unavailable"
    const val START_JOURNEY = "Start Journey"
    const val STOP_JOURNEY = "Stop Journey"
    const val PAUSE_JOURNEY = "Pause Journey"
    const val PLS_START_JOURNEY = "Please Start Journey"
    const val TRACKING_JOURNEY = "Tracking your journey"
    const val JOURNEY_PAUSED = "Journey Paused"
    const val JOURNEY_COMPLETED = "Journey Completed"
    const val RESUME_JOURNEY = "Resume Journey"
    const val GOOGLE_MAPS = "com.google.android.apps.maps"
    const val PAST_JOURNEY = "Past Journeys"
    const val DONE = "DONE"
    const val PAST_JOURNEY_NOT_AVAILABLE = "No Past Journeys Available"
    const val JOURNEY_ID = "JOURNEY ID: "
    const val JOURNEY_MILES = "Miles: "
    const val JOURNEY_KILOMETERS = "Kilometers: "
    const val KMS = "km"
    const val MI = "mi"
    const val JOURNEY_TIME = "Total time: "
    const val SHOW_MAP = "SHOW MAP"
    const val PAST_JOURNEYS = "Past Journeys"
    const val HOME = "Home"
    const val JOURNEY_ROUTE = "Journey Route"
    const val DEVELOPED_BY = "Developed by Madhav Bhavsar"
    const val JOURNEY_DB = "journeydb"
    const val LOCATION_TABLE =  "location_table"
    const val MILES_KMS = 0.621371
    const val TRACKING_LOCATION = "Tracking location..."
    const val DECIMAL_FORMAT = "%.2f"
    const val ALLOW = "Allow"
    const val DENY = "Deny"
    const val PERMISSION_REQUIRED = "Permission Required"
    const val PERMISSION_DENIED = "Permission denied"
    const val ENABLE_GPS = "Please enable GPS to start journey"
    const val GPS_DISABLED = "GPS is disabled"
    const val MISSING_LOCATION_PERMISSION = "Missing location permission"

    fun String.addCurveBrackets(): String {
        return "(${this})"
    }

    fun geoLocation(originLat: Float, originLng: Float, destLat: Float, destLng: Float): String {
        return "https://www.google.com/maps/dir/?api=1" +
                "&origin=$originLat,$originLng" +
                "&destination=$destLat,$destLng" +
                "&travelmode=driving"
    }

    fun printLocationTime(lat: String? = null, long: String? = null, time: String? = null): String {
        return "Location: (${lat},${long})\nElapsed Time: ${time}"
    }

    fun rationalText(perm: String): String {
        return "This permission is required for ${
            perm.split('.').last().replace("_", " ").lowercase()
        }."
    }
}

object Permission {
    const val NOTIFICATION_TITLE = "Notification Permission Required"
    const val LOCATION_TITLE = "Location Permission Required"
    const val ENABLE_NOTIFICATION =
        "Please enable notifications from settings to receive journey alerts."
    const val GOTO_SETTINGS = "Go to Settings"
    const val LOCATION_ENABLE = "Please enable fine location permission from settings."
    const val PACKAGE = "package"
    const val CANCEL = "Cancel"
    const val BACKGROUND_LOC_TITLE = "Background Location Required"
    const val BACKGROUND_LOC_ENABLE =
        "To track your journey even in background, please allow background location in settings."
}

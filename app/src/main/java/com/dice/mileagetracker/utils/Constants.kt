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
    const val MILES_KMS = 0.621371
    const val TRACKING_LOCATION = "Tracking location..."
    const val DECIMAL_FORMAT = "%.2f"

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
}

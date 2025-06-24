package com.dice.mileagetracker.utils

import com.dice.mileagetracker.BuildConfig

object PrefKey {
    const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID
}

object Constants {
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

    fun geoLocation(originLat: Float, originLng: Float, destLat: Float, destLng: Float): String {
        return "https://www.google.com/maps/dir/?api=1" +
                "&origin=$originLat,$originLng" +
                "&destination=$destLat,$destLng" +
                "&travelmode=driving"
    }
}

package com.dice.mileagetracker.utils

import com.dice.mileagetracker.BuildConfig

object PrefKey {
    const val PREFERENCE_NAME = BuildConfig.APPLICATION_ID
    const val JOURNEY_ID = "journeyId"
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
    const val PAST_JOURNEY = "Past Journeys"

    fun geoLocation(originLat: Float, originLng: Float, destLat: Float, destLng: Float): String {
        return "https://www.google.com/maps/dir/?api=1" +
                "&origin=$originLat,$originLng" +
                "&destination=$destLat,$destLng" +
                "&travelmode=driving"
    }

    val JOURNEY_ID = "JOURNEY ID: "
    val JOURNEY_MILES = "Miles: "
    val JOURNEY_KILOMETERS = "Kilometers: "
    val JOURNEY_TIME = "Total time: "
    val SHOW_MAP = "SHOW MAP"
    val PAST_JOURNEYS = "Past Journeys"
    val HOME = "Home"
    val JOURNEY_ROUTE = "Journey Route"
}

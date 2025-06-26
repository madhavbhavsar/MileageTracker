package com.dice.mileagetracker.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dice.mileagetracker.BuildConfig
import com.dice.mileagetracker.utils.PrefKey

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
            val journeyId = prefs.getInt(PrefKey.JOURNEY_ID, 0)
            val isTracking = prefs.getBoolean(PrefKey.IS_TRACKING, false)
            val isPaused = prefs.getBoolean(PrefKey.IS_TRACKING_PAUSED, false)

            if (journeyId != 0 && isTracking && !isPaused) {
                Intent(context, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    context.startService(this)
                }
            }
        }
    }
}
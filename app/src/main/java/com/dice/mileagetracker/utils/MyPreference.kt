package com.dice.mileagetracker.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MyPreference @Inject constructor(private var mSharedPref: SharedPreferences?) {

    var journeyIdPref: Int
        get() = getValueInt(PrefKey.JOURNEY_ID, 0)
        set(value) = setValueInt(PrefKey.JOURNEY_ID, value)

    var isTrackingPaused: Boolean
        get() = getValueBoolean(PrefKey.IS_TRACKING_PAUSED, false)
        set(value) = setValueBoolean(PrefKey.IS_TRACKING_PAUSED, value)

    var isTracking: Boolean
        get() = getValueBoolean(PrefKey.IS_TRACKING, false)
        set(value) = setValueBoolean(PrefKey.IS_TRACKING, value)

    var trackingStartTime: Long
        get() = getValueLong(PrefKey.TRACKING_START_TIME, 0L)
        set(value) = setValueLong(PrefKey.TRACKING_START_TIME, value)

    private fun getValueString(
        key: String, defaultValue: String
    ): String {
        return mSharedPref?.getString(key, defaultValue) ?: defaultValue
    }

    private fun setValueString(key: String, value: String) {
        mSharedPref?.edit {
            putString(key, value)
            apply()
        }
    }

    private fun getValueBoolean(
        key: String, defaultValue: Boolean
    ): Boolean {
        return mSharedPref?.getBoolean(key, defaultValue) ?: false
    }

    private fun setValueBoolean(key: String, value: Boolean) {
        mSharedPref?.edit {
            putBoolean(key, value)
            apply()
        }
    }

    private fun getValueInt(
        key: String, defaultValue: Int
    ): Int {
        return mSharedPref?.getInt(key, defaultValue) ?: -1
    }

    private fun setValueInt(key: String, value: Int) {
        mSharedPref?.edit {
            putInt(key, value)
            apply()
        }
    }

    private fun getValueLong(
        key: String, defaultValue: Long
    ): Long {
        return mSharedPref?.getLong(key, defaultValue) ?: -1L
    }

    private fun setValueLong(key: String, value: Long) {
        mSharedPref?.edit {
            putLong(key, value)
            apply()
        }
    }
}

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

    fun getValueString(
        key: String,
        defaultValue: String
    ): String {
        return mSharedPref?.getString(key, defaultValue) ?: defaultValue
    }

    fun setValueString(key: String, value: String) {
        mSharedPref?.edit {
            putString(key, value)
            apply()
        }
    }

    fun getValueBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean {
        return mSharedPref?.getBoolean(key, defaultValue) ?: false
    }

    fun setValueBoolean(key: String, value: Boolean) {
        mSharedPref?.edit {
            putBoolean(key, value)
            apply()
        }
    }

    fun getValueInt(
        key: String,
        defaultValue: Int
    ): Int {
        return mSharedPref?.getInt(key, defaultValue) ?: -1
    }

    fun setValueInt(key: String, value: Int) {
        mSharedPref?.edit {
            putInt(key, value)
            apply()
        }
    }
}

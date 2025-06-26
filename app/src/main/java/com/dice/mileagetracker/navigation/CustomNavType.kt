package com.dice.mileagetracker.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object CustomNavType {

    val JourneyModelType = object : NavType<JourneyModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): JourneyModel? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): JourneyModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: JourneyModel): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: JourneyModel) {
            bundle.putString(key, Json.encodeToString(value))
        }

    }
}
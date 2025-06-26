package com.dice.mileagetracker.navigation

import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes{

    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object PastJourney : Routes()

    @Serializable
    data class JourneyRoute(val journey: JourneyModel): Routes()
}
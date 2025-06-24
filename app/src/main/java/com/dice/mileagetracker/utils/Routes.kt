package com.dice.mileagetracker.utils

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
    data object MapScreen: Routes()
}
package com.dice.mileagetracker

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dice.mileagetracker.navigation.CustomNavType
import com.dice.mileagetracker.navigation.Routes
import com.dice.mileagetracker.ui.home.HomeScreenView
import com.dice.mileagetracker.ui.home.viewmodel.HomeViewModel
import com.dice.mileagetracker.ui.journeyroute.JourneyRouteScreen
import com.dice.mileagetracker.ui.pastjourney.PastJourneyScreen
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import com.dice.mileagetracker.ui.pastjourney.viewmodel.PastJourneyViewModel
import com.dice.mileagetracker.ui.splash.SplashScreenView
import kotlin.reflect.typeOf

@Composable
fun MileageTrackerApp() {
    val navHostController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navHostController,
        startDestination = Routes.SplashScreen,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 2 },
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 2 },
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(500)
            )
        },
    ) {
        composable<Routes.SplashScreen> {
            SplashScreenView(navHostController)
        }

        composable<Routes.HomeScreen> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreenView(navHostController, viewModel)
        }
        composable<Routes.PastJourney> {
            val viewModel = hiltViewModel<PastJourneyViewModel>()
            PastJourneyScreen(navHostController, viewModel)
        }
        composable<Routes.JourneyRoute>(
            typeMap = mapOf(
                typeOf<JourneyModel>() to CustomNavType.JourneyModelType
            )
        ) { args ->
            val data = args.toRoute<Routes.JourneyRoute>()
            JourneyRouteScreen(navHostController, data.journey)
        }
    }
}
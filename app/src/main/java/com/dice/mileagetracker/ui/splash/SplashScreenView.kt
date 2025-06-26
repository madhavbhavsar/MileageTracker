package com.dice.mileagetracker.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dice.mileagetracker.navigation.Routes
import com.dice.mileagetracker.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreenView(navHostController: NavHostController) {

    LaunchedEffect(true) {
        delay(3000L)
        navHostController.navigate(Routes.HomeScreen) {
            popUpTo(Routes.SplashScreen) {
                inclusive = true
            }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = Constants.SPLASH, fontSize = 35.sp)
    }
}
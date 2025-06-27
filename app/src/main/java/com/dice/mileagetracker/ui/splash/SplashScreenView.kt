package com.dice.mileagetracker.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dice.mileagetracker.R
import com.dice.mileagetracker.navigation.Routes
import com.dice.mileagetracker.ui.theme.Color_000625
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
    Box(Modifier.background(Color_000625).fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(R.drawable.img_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}
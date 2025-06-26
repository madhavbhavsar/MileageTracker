package com.dice.mileagetracker.ui.journeyroute

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dice.mileagetracker.R
import com.dice.mileagetracker.ui.common.AppBar
import com.dice.mileagetracker.ui.common.AppBarProperties
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import com.dice.mileagetracker.ui.theme.Color_021632
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.Constants.addCurveBrackets
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun JourneyRouteScreen(navController: NavController, journey: JourneyModel) {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .background(Color_FFFFFF)
    ) {
        AppBar(
            appBarProperties = AppBarProperties(
                iconButtonClick = {
                    navController.navigateUp()
                },
                appBarTitle = Constants.JOURNEY_ROUTE + Constants.SPACE + journey.journeyId?.addCurveBrackets(),
                showLeadingIcon = R.drawable.ic_arrow_left
            )
        )
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = true,
                )
            ) {
                journey.journeyPoints?.let { points ->
                    val polylinePoints = points.mapNotNull {
                        val lat = it.latitude ?: 0.0
                        val lng = it.longitude ?: 0.0
                        LatLng(lat, lng)
                    }
                    if (polylinePoints.size >= 2) {
                        Polyline(
                            points = polylinePoints,
                            color = Color_2196F3,
                            width = 5f
                        )
                    }
                }

                journey.journeyPoints?.first()?.let { item ->
                    MarkerComposable(
                        state = MarkerState(
                            position = LatLng(
                                item.latitude?.toDouble() ?: 0.0,
                                item.longitude?.toDouble() ?: 0.0
                            )
                        ), title = null
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_map_marker),
                            contentDescription = null,
                            tint = Color_021632,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                journey.journeyPoints?.last()?.let { item ->
                    MarkerComposable(
                        state = MarkerState(
                            position = LatLng(
                                item.latitude?.toDouble() ?: 0.0,
                                item.longitude?.toDouble() ?: 0.0
                            )
                        ), title = null
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_map_marker),
                            contentDescription = null,
                            tint = Color_021632,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}
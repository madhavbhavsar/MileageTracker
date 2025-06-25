package com.dice.mileagetracker.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.dice.mileagetracker.R
import com.dice.mileagetracker.ui.common.AppBar
import com.dice.mileagetracker.ui.common.AppBarProperties
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.Constants
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavController) {

}

@Composable
@Preview
fun MapScreenView() {
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxSize()
            .background(Color_FFFFFF)
    ) {
        AppBar(
            appBarProperties = AppBarProperties(
                iconButtonClick = {},
                appBarTitle = Constants.JOURNEY_ROUTE,
                showLeadingIcon = R.drawable.ic_arrow_left
            )
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = true,
                )
            ) {

                /*MarkerComposable(
                    onClick = { _ ->
                                    onMarkerClick.invoke(index, item)
                        false
                    },
                    state = MarkerState(
                        position = LatLng(
                            *//* item.latitude?.toDouble() ?: 0.0,
                             item.longitude?.toDouble() ?: 0.0*//*
                        )
                    ), title = null
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_map_marker),
                        contentDescription = null,
                        tint = Color_021632,
                        modifier = Modifier.size(36.dp)
                    )
                }*/
            }
        }
    }
}
package com.dice.mileagetracker.ui.home

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dice.mileagetracker.location.LocationService
import com.dice.mileagetracker.navigation.Routes
import com.dice.mileagetracker.ui.common.AppAlertDialog
import com.dice.mileagetracker.ui.common.AppBar
import com.dice.mileagetracker.ui.common.AppBarProperties
import com.dice.mileagetracker.ui.common.LoadingProgressBar
import com.dice.mileagetracker.ui.home.viewmodel.HomeViewModel
import com.dice.mileagetracker.ui.home.viewmodel.JourneyState
import com.dice.mileagetracker.ui.theme.Color_021632
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.PermissionHandlerFlow
import com.dice.mileagetracker.utils.singleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreenView(navHostController: NavHostController, viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var triggerPermissionFlow by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // button states
    LaunchedEffect(uiState.journeyState) {
        when (uiState.journeyState) {
            JourneyState.New, JourneyState.Stop -> {
                viewModel.startEnabled(true)
                viewModel.stopEnabled(false)
                viewModel.pauseResEnabled(false)
            }

            JourneyState.Started, JourneyState.Paused, JourneyState.Resumed -> {
                viewModel.startEnabled(false)
                viewModel.stopEnabled(true)
                viewModel.pauseResEnabled(true)
            }
        }
    }
    PermissionHandlerFlow(
        start = triggerPermissionFlow,
        onSuccess = {
            if (uiState.startEnabled) {
                viewModel.updateJourneyState(JourneyState.Started)
                viewModel.updateJourneyId = (viewModel.updateJourneyId + 1)
                viewModel.updateTrackingStartTime = System.currentTimeMillis()
                viewModel.updateIsTracking = true
                viewModel.updateIsTrackingPaused = false

                Intent(context, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    context.startService(this)
                }
            }
        },
        onComplete = {
            triggerPermissionFlow = false
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color_FFFFFF),
    ) {
        AppBar(
            appBarProperties = AppBarProperties(
                appBarTitle = Constants.HOME,
            )
        )
        Column(
            Modifier
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (uiState.startEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .singleClickable {
                        triggerPermissionFlow = true
                    }
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                text = Constants.START_JOURNEY,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (uiState.pauseResEnabled) Color_2196F3 else Color_2196F3.copy(
                            alpha = 0.5f
                        ), shape = RoundedCornerShape(8.dp)
                    )
                    .singleClickable {
                        //pause journey
                        if (uiState.pauseResEnabled) {
                            when (uiState.journeyState) {
                                JourneyState.Started, JourneyState.Resumed -> {
                                    viewModel.updateJourneyState(JourneyState.Paused)
                                    viewModel.updateIsTracking = false
                                    viewModel.updateIsTrackingPaused = true
                                    Intent(context, LocationService::class.java).apply {
                                        action = LocationService.ACTION_PAUSE
                                        context.startService(this)
                                    }
                                }

                                JourneyState.Paused -> {
                                    viewModel.updateJourneyState(JourneyState.Resumed)
                                    viewModel.updateIsTracking = true
                                    viewModel.updateIsTrackingPaused = false
                                    Intent(context, LocationService::class.java).apply {
                                        action = LocationService.ACTION_RESUME
                                        context.startService(this)
                                    }
                                }

                                else -> {/*empty*/
                                }
                            }
                        }
                    }
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                text = when (uiState.journeyState) {
                    JourneyState.Started, JourneyState.Resumed -> {
                        Constants.PAUSE_JOURNEY
                    }

                    JourneyState.Paused -> {
                        Constants.RESUME_JOURNEY
                    }

                    else -> {/*empty*/
                        Constants.PAUSE_JOURNEY
                    }
                },
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (uiState.stopEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .singleClickable {
                        // stop journey
                        if (uiState.stopEnabled) {
                            viewModel.updateJourneyState(JourneyState.Stop)

                            viewModel.updateIsTracking = false
                            viewModel.updateIsTrackingPaused = false

                            Intent(context, LocationService::class.java).apply {
                                action = LocationService.ACTION_STOP
                                context.startService(this)
                            }

                            viewModel.fetchCurrentJourney()
                        }
                    }
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                text = Constants.STOP_JOURNEY,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color_021632.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 40.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                text = when (uiState.journeyState) {
                    JourneyState.New -> Constants.PLS_START_JOURNEY
                    JourneyState.Started -> Constants.TRACKING_JOURNEY
                    JourneyState.Paused -> Constants.JOURNEY_PAUSED
                    JourneyState.Stop -> Constants.JOURNEY_COMPLETED
                    JourneyState.Resumed -> Constants.TRACKING_JOURNEY
                },
                fontSize = 18.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (uiState.startEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .singleClickable {
                        if (uiState.startEnabled) {
                            navHostController.navigate(Routes.PastJourney)
                        }
                    }
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                text = Constants.PAST_JOURNEY,
                fontSize = 16.sp
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                text = Constants.DEVELOPED_BY,
                fontSize = 10.sp
            )
        }
    }

    LoadingProgressBar(visible = uiState.isLoading)
    AppAlertDialog(
        isVisible = uiState.showDialog != null,
        journeyModel = uiState.showDialog,
        mapNavigate = {
            scope.launch {
                viewModel.updateJourneyState(JourneyState.New)
                viewModel.showDialog(null)
                delay(500L) // delay for smooth transition
                navHostController.navigate(Routes.JourneyRoute(journey = it))
            }
        },
        onDismissRequest = {
            scope.launch {
                viewModel.updateJourneyState(JourneyState.New)
                viewModel.showDialog(null)
            }
        }
    )
}
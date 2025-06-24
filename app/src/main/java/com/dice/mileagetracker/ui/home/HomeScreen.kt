package com.dice.mileagetracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dice.mileagetracker.ui.home.viewmodel.HomeViewModel
import com.dice.mileagetracker.ui.home.viewmodel.JourneyState
import com.dice.mileagetracker.ui.theme.Color_021632
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.singleClickable

@Composable
fun HomeScreenView(navHostController: NavHostController, viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.journeyState) {
        when (uiState.journeyState) {
            JourneyState.New -> {
                viewModel.startEnabled(true)
                viewModel.stopEnabled(true)
                viewModel.pauseResEnabled(true)
            }

            JourneyState.Started, JourneyState.Paused, JourneyState.Resumed -> {
                viewModel.startEnabled(false)
                viewModel.stopEnabled(true)
                viewModel.pauseResEnabled(true)
            }

            JourneyState.Stop -> {
                viewModel.startEnabled(true)
                viewModel.stopEnabled(false)
                viewModel.pauseResEnabled(false)
            }

        }
    }
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(100.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (uiState.startEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f))
                .singleClickable {
                    // start journey
                    if (uiState.startEnabled) {
                        viewModel.updateJourneyState(JourneyState.Started)
                    }
                }
                .padding(10.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = Constants.START_JOURNEY,
            fontSize = 24.sp
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (uiState.pauseResEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f))
                .singleClickable {
                    //pause journey
                    if (uiState.pauseResEnabled) {
                        when (uiState.journeyState) {
                            JourneyState.Started, JourneyState.Resumed -> {
                                viewModel.updateJourneyState(JourneyState.Paused)
                            }

                            JourneyState.Paused -> {
                                viewModel.updateJourneyState(JourneyState.Resumed)
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
            } ,
            fontSize = 24.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (uiState.stopEnabled) Color_2196F3 else Color_2196F3.copy(alpha = 0.5f))
                .singleClickable {
                    // stop journey
                    if (uiState.stopEnabled) {
                        viewModel.updateJourneyState(JourneyState.Stop)
                    }
                }
                .padding(10.dp),
            color = Color.White,
            textAlign = TextAlign.Center,
            text = Constants.STOP_JOURNEY,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(50.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = Color_021632.copy(alpha = 0.15f))
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
                fontSize = 20.sp
            )
        }
    }
}
package com.dice.mileagetracker.ui.pastjourney

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dice.mileagetracker.R
import com.dice.mileagetracker.navigation.Routes
import com.dice.mileagetracker.ui.common.AppBar
import com.dice.mileagetracker.ui.common.AppBarProperties
import com.dice.mileagetracker.ui.common.LoadingProgressBar
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import com.dice.mileagetracker.ui.pastjourney.viewmodel.PastJourneyViewModel
import com.dice.mileagetracker.ui.theme.Color_021632
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.singleClickable

@Composable
fun PastJourneyScreen(navController: NavHostController, viewModel: PastJourneyViewModel) {
    val uiState by viewModel.uiState.collectAsState()
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
                appBarTitle = Constants.PAST_JOURNEYS,
                showLeadingIcon = R.drawable.ic_arrow_left
            )
        )
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(16.dp))
            uiState.journeyList?.let { list ->
                list.forEachIndexed { index, item ->
                    JourneyItemCard(item) {
                        navController.navigate(Routes.JourneyRoute(item))
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
            if (uiState.journeyList.isNullOrEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = Constants.PAST_JOURNEY_NOT_AVAILABLE,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    LoadingProgressBar(visible = uiState.isLoading)
}

@Composable
fun JourneyItemCard(journeyModel: JourneyModel, onMapClick: () -> Unit = {}) {
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .border(
                color = Color_021632.copy(alpha = 0.15f),
                width = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = Constants.JOURNEY_ID + Constants.SPACE + journeyModel.journeyId.toString(),
                fontSize = 16.sp,
                maxLines = 1,
                color = Color_021632,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = Constants.JOURNEY_MILES,
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = Constants.JOURNEY_KILOMETERS,
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = Constants.JOURNEY_TIME,
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = journeyModel.journeyMiles.toString() + Constants.SPACE + Constants.MI,
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = journeyModel.journeyKms.toString() + Constants.SPACE + Constants.KMS,
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = journeyModel.journeyDuration.toString(),
                    fontSize = 12.sp,
                    color = Color_021632.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = Color_021632.copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Row(modifier = Modifier.singleClickable {
            onMapClick.invoke()
        }, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Constants.SHOW_MAP,
                fontSize = 12.sp,
                color = Color_2196F3,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                modifier = Modifier.padding(bottom = 2.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_terms_right),
                contentDescription = null,
                tint = Color_021632.copy(alpha = 0.7f)
            )
        }
    }
}
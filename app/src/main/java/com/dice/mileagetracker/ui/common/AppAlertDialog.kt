package com.dice.mileagetracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dice.mileagetracker.ui.pastjourney.JourneyItemCard
import com.dice.mileagetracker.ui.pastjourney.viewmodel.JourneyModel
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.Constants
import com.dice.mileagetracker.utils.singleClickable

@Composable
fun AppAlertDialog(
    isVisible: Boolean = false,
    journeyModel: JourneyModel? = null,
    mapNavigate: (JourneyModel) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = {
                onDismissRequest.invoke()
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color_FFFFFF),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    journeyModel?.let { item ->
                        JourneyItemCard(journeyModel = item) {
                            mapNavigate.invoke(item)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color_2196F3,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .singleClickable {
                                onDismissRequest.invoke()
                            }
                            .padding(10.dp),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        text = Constants.DONE,
                        fontSize = 16.sp
                    )
                }
            }

        }
    }
}
package com.dice.mileagetracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dice.mileagetracker.ui.theme.Color_2196F3
import com.dice.mileagetracker.ui.theme.Color_FFFFFF

@Composable
fun LoadingProgressBar(visible: Boolean) {
    if (visible) {
        Surface(
            Modifier.background(Color.Transparent),
            color = Color.Transparent,
        ) {
            Dialog(
                onDismissRequest = {/*empty*/ },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.size(100.dp),
                        colors = CardDefaults.cardColors(containerColor = Color_FFFFFF),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color_2196F3)
                        }
                    }
                }
            }
        }
    }
}
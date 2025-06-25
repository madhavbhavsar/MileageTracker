package com.dice.mileagetracker.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dice.mileagetracker.ui.theme.Color_021632
import com.dice.mileagetracker.ui.theme.Color_FFFFFF
import com.dice.mileagetracker.utils.singleClickable


data class AppBarProperties(
    val iconButtonClick: () -> Unit = {},
    val showLeadingIcon: Int? = null,
    val appBarTitle: String? = null,
)

@Composable
fun AppBar(appBarProperties: AppBarProperties = AppBarProperties()) {
    Box(
        Modifier
            .background(Color_021632)
            .statusBarsPadding()
            .height(56.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (appBarProperties.showLeadingIcon != null) {
                Icon(
                    imageVector = ImageVector.vectorResource(appBarProperties.showLeadingIcon),
                    modifier = Modifier
                        .singleClickable {
                            appBarProperties.iconButtonClick.invoke()
                        }
                        .size(20.dp),
                    tint = Color_FFFFFF,
                    contentDescription = null
                )
            }
            if (appBarProperties.appBarTitle != null) {
                Spacer(
                    Modifier.width(
                        if (appBarProperties.showLeadingIcon != null) 20.dp
                        else 0.dp
                    )
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 20.dp),
                    fontSize = 18.sp,
                    text = appBarProperties.appBarTitle.toString(),
                    color = Color_FFFFFF,
                )
            }
        }
    }
}
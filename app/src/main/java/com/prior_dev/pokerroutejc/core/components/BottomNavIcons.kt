package com.prior_dev.pokerroutejc.core.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.R

@Composable
fun BottomNavIcons(
    @DrawableRes painterResourceId: Int,
    @StringRes contentDescriptionId: Int,
    isSelected: Boolean
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = painterResourceId),
            contentDescription = stringResource(id = contentDescriptionId),
            modifier = Modifier.size(24.dp)
        )
        if(isSelected){
            Text(
                text = stringResource(id = contentDescriptionId)
            )
        }
    }
}
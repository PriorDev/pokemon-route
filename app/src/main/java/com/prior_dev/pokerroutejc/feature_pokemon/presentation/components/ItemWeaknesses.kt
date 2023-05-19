package com.prior_dev.pokerroutejc.feature_pokemon.presentation.components

import android.graphics.drawable.PaintDrawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.components.ItemType
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData


@Composable
fun ItemWeaknesses(
    weakness: List<TypeData>,
    @DrawableRes drawable: Int,
    @StringRes drawableDescription: Int
) {
    weakness.let{ enemyTypes ->
        if(enemyTypes.isNotEmpty()){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = MaterialTheme.colors.primary)
            ) {
                Image(
                    painter = painterResource(id = drawable),
                    contentDescription = stringResource(id = drawableDescription),
                    modifier = Modifier.size(20.dp)
                )

                Box(
                    modifier = Modifier
                        .height(enemyTypes.size.times(16).dp)
                        .width(1.dp)
                        .background(MaterialTheme.colors.primary)
                )

                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    enemyTypes.forEach{

                    }
                }
            }
        }
    }
}
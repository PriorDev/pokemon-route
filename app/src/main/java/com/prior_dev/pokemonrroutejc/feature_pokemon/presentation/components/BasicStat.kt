package com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.prior_dev.pokemonrroutejc.ui.theme.HighStat
import com.prior_dev.pokemonrroutejc.ui.theme.LowStat
import com.prior_dev.pokemonrroutejc.ui.theme.MiddleStat

@Composable
fun BasicStat(
    name: String,
    value: Int,
    maxValue: Int = 150,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
) {
    var animationPlayed by remember{
        mutableStateOf(false)
    }

    val curPercent = animateFloatAsState(
        targetValue = if(animationPlayed){
            value / maxValue.toFloat()
        } else 0f,
        animationSpec = tween(animDuration, animDelay)
    )

    val currentStat = (curPercent.value * maxValue).toInt()

    val color = if(currentStat <= 40){
        LowStat
    }else if(currentStat in 41..99){
        MiddleStat
    }else{
        HighStat
    }

    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(
                if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
            )
    ){
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(color)
                .padding(horizontal = 8.dp)
        ) {

        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentStat.toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }

}
package com.priorDev.pokerroutejc.core.presentation.components

import androidx.annotation.DrawableRes
import com.priorDev.pokerroutejc.navigation.Routes

data class NavBottomItems(
    val route: Routes,
    @DrawableRes val icon: Int,
    val title: String,
    val strRoute: String
)

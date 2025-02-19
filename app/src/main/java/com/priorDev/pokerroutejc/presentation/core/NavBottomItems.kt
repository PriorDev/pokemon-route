package com.priorDev.pokerroutejc.presentation.core

import androidx.annotation.DrawableRes
import com.priorDev.pokerroutejc.ui.Routes

data class NavBottomItems(
    val route: Routes,
    @DrawableRes val icon: Int,
    val title: String,
    val strRoute: String
)

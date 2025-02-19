package com.priorDev.pokerroutejc.core

import androidx.annotation.DrawableRes
import com.priorDev.pokerroutejc.utils.Routes

data class NavBottomItems <T: Routes>(
    val route: T,
    @DrawableRes val icon: Int,
    val title: String,
    val strRoute: String
)

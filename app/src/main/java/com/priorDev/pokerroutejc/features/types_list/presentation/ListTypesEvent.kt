package com.priorDev.pokerroutejc.features.types_list.presentation

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed class ListTypesEvent {
    data object Refresh : ListTypesEvent()
    data class Navigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : ListTypesEvent()
}

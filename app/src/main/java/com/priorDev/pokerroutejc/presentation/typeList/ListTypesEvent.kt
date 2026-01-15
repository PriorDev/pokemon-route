package com.priorDev.pokerroutejc.presentation.typeList

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

sealed class ListTypesEvent {
    data object Refresh : ListTypesEvent()
    data class Navigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : ListTypesEvent()

    data object OnRetryGetAllTypes : ListTypesEvent()
    data object OnDismissError : ListTypesEvent()
}

package com.priorDev.pokerroutejc.presentation.versionGroups

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

sealed class VersionGroupEvent {
    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : VersionGroupEvent()

    data object OnToggleOrder : VersionGroupEvent()
}

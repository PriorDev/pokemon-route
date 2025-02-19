package com.priorDev.pokerroutejc.featurePokemon.presentation.search

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.utils.Routes

sealed class PkSearchEvent {
    data class OnSearch(val query: String): PkSearchEvent()
    data object OnNavigateUp: PkSearchEvent()

    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ): PkSearchEvent()
}

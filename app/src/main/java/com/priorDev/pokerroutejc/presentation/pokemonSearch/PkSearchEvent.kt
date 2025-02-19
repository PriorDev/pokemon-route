package com.priorDev.pokerroutejc.presentation.pokemonSearch

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.ui.Routes

sealed class PkSearchEvent {
    data class OnSearch(val query: String) : PkSearchEvent()
    data object OnNavigateUp : PkSearchEvent()

    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PkSearchEvent()
}

package com.priorDev.pokerroutejc.features.pokemon_search.presentation

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.navigation.Routes

sealed class PkSearchEvent {
    data class OnSearch(val query: String) : PkSearchEvent()
    data object OnNavigateUp : PkSearchEvent()

    data class OnNavigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PkSearchEvent()
}

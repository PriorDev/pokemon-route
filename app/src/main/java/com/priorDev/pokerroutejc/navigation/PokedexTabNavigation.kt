package com.priorDev.pokerroutejc.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.features.pokedex.presentation.pokedexWrapper
import com.priorDev.pokerroutejc.features.pokedex_selection.presentation.versionGroupWrapper
import com.priorDev.pokerroutejc.navigation.Routes

fun NavGraphBuilder.pokedexNavigation() {
    navigation<Routes.PokedexNav>(
        startDestination =  Routes.VersionGroups
    ) {
        versionGroupWrapper()

        pokedexWrapper()
    }
}
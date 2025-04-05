package com.priorDev.pokerroutejc.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.priorDev.pokerroutejc.presentation.pokedex.pokedexWrapper
import com.priorDev.pokerroutejc.presentation.versionGroups.versionGroupWrapper
import com.priorDev.pokerroutejc.ui.Routes

fun NavGraphBuilder.pokedexNavigation() {
    navigation<Routes.PokedexNav>(
        startDestination =  Routes.VersionGroups
    ) {
        versionGroupWrapper()

        pokedexWrapper()
    }
}
package com.priorDev.pokerroutejc.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.priorDev.pokerroutejc.BottomNavBar
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonNavigation
import com.priorDev.pokerroutejc.featureTypes.presentation.typesNavigation
import com.priorDev.pokerroutejc.utils.GlobalEventHandler

@Composable
fun MainComposable() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->

        GlobalEventHandler(navController)

        NavHost(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            startDestination = Routes.TypeNav,
            route = Routes.MainNav::class
        ) {
            pokemonNavigation()

            typesNavigation()
        }
    }
}

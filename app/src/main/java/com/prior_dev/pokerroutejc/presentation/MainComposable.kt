package com.prior_dev.pokerroutejc.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.prior_dev.pokerroutejc.MenuBottomNavBar
import com.prior_dev.pokerroutejc.core.routes.RoutesMenu
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.pokemonNavigation
import com.prior_dev.pokerroutejc.feature_types.presentation.typesNavigation
import com.prior_dev.pokerroutejc.utils.GlobalEventHandler

@Composable
fun MainComposable() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MenuBottomNavBar(navController = navController)
        }
    ) { innerPadding ->

        GlobalEventHandler(navController)

        NavHost(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            startDestination = RoutesType.ROUTE_NAME,
            route = RoutesMenu.ROUTE_NAME
        ){
            pokemonNavigation()

            typesNavigation()
        }
    }
}

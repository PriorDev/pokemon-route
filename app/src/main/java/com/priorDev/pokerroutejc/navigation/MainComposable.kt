package com.priorDev.pokerroutejc.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.presentation.components.NavBottomItems
import com.priorDev.pokerroutejc.core.utils.GlobalEventHandler
import com.priorDev.pokerroutejc.navigation.Routes
import com.priorDev.pokerroutejc.core.presentation.toRoute

@Composable
fun MainComposable() {
    val navController = rememberNavController()

    val bottomItems = listOf(
        NavBottomItems(
            route = Routes.TypeNav,
            icon = R.drawable.outline_radio_button_checked_24,
            title = stringResource(R.string.types),
            strRoute = Routes.TypeNav.serializer().descriptor.toRoute()
        ),
        NavBottomItems(
            route = Routes.PokemonNav,
            icon = R.drawable.icon_pokeball,
            title = stringResource(R.string.pokemons),
            strRoute = Routes.PokemonNav.serializer().descriptor.toRoute()
        ),
        NavBottomItems(
            route = Routes.PokedexNav,
            icon = R.drawable.outline_pokedex_icon,
            title = stringResource(R.string.pokedex),
            strRoute = Routes.PokedexNav.serializer().descriptor.toRoute()
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                bottomItems = bottomItems
            )
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

            pokedexNavigation()
        }
    }
}

package com.prior_dev.pokerroutejc.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prior_dev.pokerroutejc.MenuBottomNavBar
import com.prior_dev.pokerroutejc.core.routes.RoutesMenu
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsViewModel
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.pokemonNavigation
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.typesNavigation
import com.prior_dev.pokerroutejc.utils.GlobalEventChannel
import com.prior_dev.pokerroutejc.utils.GlobalEventHandler
import com.prior_dev.pokerroutejc.utils.ObserveEvents
import com.prior_dev.pokerroutejc.utils.OneTimeEvent
import dagger.hilt.android.EntryPointAccessors

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

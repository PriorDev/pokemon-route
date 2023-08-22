package com.prior_dev.pokerroutejc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
import com.prior_dev.pokerroutejc.core.routes.RoutesMenu
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsUiEvents
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsViewModel
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchUiEvent
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeView
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypeViewModel
import com.prior_dev.pokerroutejc.feature_types.presentation.list.ListTypesUiEvent
import com.prior_dev.pokerroutejc.ui.theme.PokemonRRouteJCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonRRouteJCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()

                    Scaffold(
                        bottomBar = {
                            MenuBottomNavBar(navController = navController)
                        }
                    ) { innerPadding ->
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
            }
        }
    }

    private fun NavGraphBuilder.pokemonNavigation() {
        navigation(
            route = RoutesPokemon.ROUTE_NAME,
            startDestination = RoutesPokemon.SearchRoute.route
        ){
            composable(RoutesPokemon.SearchRoute.route){
                val viewModel = hiltViewModel<PokemonSearchViewModel>()
                val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
                val pokemonList = viewModel.pokemonList

                PokemonSearchView(
                    commonStates = commonStates.value,
                    pokemonList = pokemonList,
                    onEvent = viewModel::onEvent,
                    onUIEvent = ::onPokemonSearchUiEvent
                )
            }

            composable(
                route = RoutesPokemon.PokemonDetails.route,
                arguments = listOf(
                    navArgument(RoutesPokemon.PokemonDetails.argPokemonName){
                        type = NavType.StringType
                    }
                )
            ){
                val viewModel = hiltViewModel<PokemonDetailsViewModel>()
                val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
                val states = viewModel.states.collectAsStateWithLifecycle()
                val moveList = viewModel.moves

                PokemonDetailsView(
                    commonStates = commonStates.value,
                    states = states.value,
                    movesList =  moveList,
                    onEvents = viewModel::onEvent,
                    onUiEvents = ::onPokemonDetailsUiEvent
                )
            }

            composable(
                route = RoutesPokemon.TypeDetails.route,
                arguments = listOf(
                    navArgument(name = RoutesPokemon.TypeDetails.argType){
                        type = NavType.IntType
                    }
                )
            ){
                val viewModel: DetailsTypeViewModel = hiltViewModel()
                DetailsTypeView(viewModel)
            }
        }
    }

    private fun onPokemonSearchUiEvent(event: PokemonSearchUiEvent){
        when(event){
            is PokemonSearchUiEvent.openPokemonDetailsView -> {
                navController.navigate(RoutesPokemon.PokemonDetails.getRoute(event.pokemon))
            }
        }
    }

    private fun onPokemonDetailsUiEvent(event: PokemonDetailsUiEvents){
        when(event){
            is PokemonDetailsUiEvents.OpenTypeDetails ->
                navController.navigate(RoutesPokemon.TypeDetails.getRoute(event.typeId))
        }
    }

    private fun NavGraphBuilder.typesNavigation(){
        navigation(
            startDestination = RoutesType.TypesList.route,
            route = RoutesType.ROUTE_NAME
        ){
            composable(RoutesType.TypesList.route){
                val viewModel = hiltViewModel<ListTypeViewModel>()
                val commonStates = viewModel.commonStates.collectAsState()
                val states = viewModel.states.collectAsState()

                ListTypeView(
                    commonStates = commonStates.value,
                    states = states.value,
                    onEvent = viewModel::onEvent,
                    onUiEvent = ::onListTypeUIEvent
                )
            }

            composable(
                route = RoutesType.TypeDetails.route,
                arguments = listOf(
                    navArgument(name = RoutesType.TypeDetails.argType){
                        type = NavType.IntType
                    }
                )
            ){
                val viewModel: DetailsTypeViewModel = hiltViewModel()
                DetailsTypeView(viewModel)
            }
        }
    }

    private fun onListTypeUIEvent(event: ListTypesUiEvent){
        when(event){
            is ListTypesUiEvent.openTypesDetailScreen -> {
                navController.navigate(RoutesType.TypeDetails.getRoute(event.typeId))
            }
        }
    }
}

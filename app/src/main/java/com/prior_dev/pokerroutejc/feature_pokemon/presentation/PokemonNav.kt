package com.prior_dev.pokerroutejc.feature_pokemon.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.details.PokemonDetailsView
import com.prior_dev.pokerroutejc.feature_pokemon.presentation.search.PokemonSearchView
import com.prior_dev.pokerroutejc.feature_types.presentation.details.DetailsTypeView

@Composable
fun PokemonNav(
    menuNav: NavHostController
) {
    val navPokemon = rememberNavController()

    NavHost(
        navController = navPokemon,
        startDestination = RoutesPokemon.SearchRoute.route,
        route = RoutesPokemon.ROUTE_NAME
    ){
        composable(RoutesPokemon.SearchRoute.route){ PokemonSearchView(navPokemon) }
        composable(
            route = RoutesPokemon.PokemonDetails.route,
            arguments = listOf(
                navArgument(RoutesPokemon.PokemonDetails.argPokemonName){
                    type = NavType.StringType
                }
            )
        ){
            PokemonDetailsView(navPokemon)
        }
        composable(
            route = RoutesPokemon.TypeDetails.route,
            arguments = listOf(
                navArgument(name = RoutesPokemon.TypeDetails.argType){
                    type = NavType.IntType
                }
            )
        ){
            DetailsTypeView()
        }
    }
}
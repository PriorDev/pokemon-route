package com.prior_dev.pokemonrroutejc.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.PokemonNav
import com.prior_dev.pokemonrroutejc.feature_pokemon.presentation.search.PokemonSearchView
import com.prior_dev.pokemonrroutejc.feature_types.presentation.TypeNav
import com.prior_dev.pokemonrroutejc.ui.theme.PokemonRRouteJCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonRRouteJCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navMenu = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            MenuBottomNavBar(navMenu = navMenu)
                        }
                    ) { innerPadding ->
                        NavHost(
                            modifier = Modifier
                                .padding(innerPadding),
                            navController = navMenu,
                            startDestination = RoutesMenu.NavPokemonRoute.route,
                            route = RoutesMenu.ROUTE_NAME
                        ){
                            composable(RoutesMenu.NavTypesRoute.route){ TypeNav(navMenu) }
                            composable(RoutesMenu.NavPokemonRoute.route){ PokemonNav(navMenu) }
                        }
                    }
                }
            }
        }
    }
}

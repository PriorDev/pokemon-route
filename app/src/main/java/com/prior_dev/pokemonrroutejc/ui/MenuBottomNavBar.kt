package com.prior_dev.pokemonrroutejc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.prior_dev.pokemonrroutejc.R

@Composable
fun MenuBottomNavBar(
    navMenu: NavHostController
){
    val navBackStackEntry by navMenu.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isTypeSelect = currentDestination?.hierarchy?.any{
        it.route == RoutesMenu.NavTypesRoute.route
    } == true

    val isPokemonsSelect = currentDestination?.hierarchy?.any{
        it.route == RoutesMenu.NavPokemonRoute.route
    } == true

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ){
        BottomNavigationItem(
            selected = isTypeSelect,
            onClick = {
                navMenu.navigate(RoutesMenu.NavTypesRoute.route){
                    popUpTo(navMenu.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_radio_button_checked_24),
                        contentDescription = stringResource(id = R.string.types),
                    )
                    if(isTypeSelect){
                        Text(
                            text = stringResource(id = R.string.types)
                        )
                    }
                }
            }
        )

        BottomNavigationItem(
            selected = isPokemonsSelect,
            onClick = {
                navMenu.navigate(RoutesMenu.NavPokemonRoute.route){
                    popUpTo(navMenu.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_pokeball),
                        contentDescription = stringResource(id = R.string.types),
                        modifier = Modifier.size(24.dp)
                    )
                    if(isPokemonsSelect){
                        Text(
                            text = stringResource(id = R.string.pokemons)
                        )
                    }
                }
            }
        )
    }
}
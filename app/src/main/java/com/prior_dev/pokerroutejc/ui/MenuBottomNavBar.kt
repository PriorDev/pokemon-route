package com.prior_dev.pokerroutejc.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.prior_dev.pokerroutejc.R
import com.prior_dev.pokerroutejc.core.components.BottomNavIcons
import com.prior_dev.pokerroutejc.core.routes.RoutesMenu
import com.prior_dev.pokerroutejc.core.routes.RoutesPokemon
import com.prior_dev.pokerroutejc.core.routes.RoutesType

@Composable
fun MenuBottomNavBar(
    navController: NavHostController
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isTypeSelected = currentDestination?.hierarchy?.any{
        it.route == RoutesType.ROUTE_NAME
    } == true

    val isPokemonsSelected = currentDestination?.hierarchy?.any{
        it.route == RoutesPokemon.ROUTE_NAME
    } == true

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ){
        BottomNavigationItem(
            selected = isTypeSelected,
            onClick = {
                navController.navigate(RoutesType.ROUTE_NAME){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                BottomNavIcons(
                    painterResourceId = R.drawable.outline_radio_button_checked_24,
                    contentDescriptionId = R.string.types,
                    isSelected = isTypeSelected
                )
            }
        )

        BottomNavigationItem(
            selected = isPokemonsSelected,
            onClick = {
                navController.navigate(RoutesPokemon.ROUTE_NAME){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                BottomNavIcons(
                    painterResourceId = R.drawable.icon_pokeball,
                    contentDescriptionId = R.string.pokemons,
                    isSelected = isPokemonsSelected
                )
            }
        )
    }
}
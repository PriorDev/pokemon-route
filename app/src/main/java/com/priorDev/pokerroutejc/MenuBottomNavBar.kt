package com.priorDev.pokerroutejc

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.priorDev.pokerroutejc.core.routes.RoutesPokemon
import com.priorDev.pokerroutejc.core.routes.RoutesType

@Composable
fun MenuBottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isTypeSelected = currentDestination?.hierarchy?.any {
        it.route == RoutesType.ROUTE_NAME
    } == true

    val isPokemonsSelected = currentDestination?.hierarchy?.any {
        it.route == RoutesPokemon.ROUTE_NAME
    } == true

    NavigationBar {
        NavigationBarItem(
            selected = isTypeSelected,
            onClick = { onNavigationItemClick(navController, RoutesType.ROUTE_NAME) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_radio_button_checked_24),
                    contentDescription = stringResource(R.string.types)
                )
            },
            label = { Text(text = stringResource(R.string.types)) }
        )

        NavigationBarItem(
            selected = isPokemonsSelected,
            onClick = { onNavigationItemClick(navController, RoutesPokemon.ROUTE_NAME) },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_pokeball),
                    contentDescription = stringResource(R.string.pokemons),
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text(text = stringResource(R.string.pokemons)) }
        )
    }
}

fun onNavigationItemClick(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

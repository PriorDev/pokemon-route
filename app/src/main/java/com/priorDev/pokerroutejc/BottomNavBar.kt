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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.utils.navigateToTab
import com.priorDev.pokerroutejc.utils.toRoute

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedTab = currentDestination?.parent?.route.orEmpty()

    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == Routes.TypeNav.serializer().descriptor.toRoute(),
            onClick = {
                navController.navigateToTab(Routes.TypeNav)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.outline_radio_button_checked_24),
                    contentDescription = stringResource(R.string.types)
                )
            },
            label = {
                Text(text = stringResource(R.string.types))
            }
        )

        NavigationBarItem(
            selected = selectedTab == Routes.PokemonNav.serializer().descriptor.toRoute(),
            onClick = {
                navController.navigateToTab(Routes.PokemonNav)
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_pokeball),
                    contentDescription = stringResource(R.string.pokemons),
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(text = stringResource(R.string.pokemons))
            }
        )
    }
}

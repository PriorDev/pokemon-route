package com.priorDev.pokerroutejc.navigation

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.presentation.components.NavBottomItems
import com.priorDev.pokerroutejc.core.presentation.navigateToTab

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomItems: List<NavBottomItems>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val selectedTab = currentDestination?.parent?.route.orEmpty()

    NavigationBar {
        bottomItems.forEach { item ->
            val isSelected = selectedTab == item.strRoute
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigateToTab(item.route)
                    }
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(R.string.go_to_tab, item.title)
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

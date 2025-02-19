package com.priorDev.pokerroutejc.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun GlobalEventHandler(navController: NavHostController) {
    ObserveEvents(flow = GlobalEventChannel.eventChannel) { event ->
        when (event) {
            is OneTimeEvent.OnNavigate -> {
                navController.navigate(
                    route = event.route,
                    builder = event.navOptions
                )
            }

            OneTimeEvent.OnNavigateUp -> navController.navigateUp()
        }
    }
}

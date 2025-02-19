package com.priorDev.pokerroutejc.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.priorDev.pokerroutejc.presentation.utils.ObserveEvents

@Composable
fun GlobalEventHandler(navController: NavHostController) {
    ObserveEvents(flow = GlobalEventChannelImp.eventChannel) { event ->
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

package com.priorDev.pokerroutejc.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.priorDev.pokerroutejc.presentation.utils.ObserveEvents
import org.koin.androidx.compose.get

@Composable
fun GlobalEventHandler(navController: NavHostController) {
    val globalChannel = get<GlobalEventChannel>()

    ObserveEvents(globalChannel.eventChannel) { event ->
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

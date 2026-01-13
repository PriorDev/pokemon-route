package com.priorDev.pokerroutejc.core.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.priorDev.pokerroutejc.core.presentation.ObserveEvents
import org.koin.compose.koinInject

@Composable
fun GlobalEventHandler(navController: NavHostController) {
    val globalChannel = koinInject<GlobalEventChannel>()

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

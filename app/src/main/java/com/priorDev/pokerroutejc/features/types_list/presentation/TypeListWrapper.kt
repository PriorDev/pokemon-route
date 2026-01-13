package com.priorDev.pokerroutejc.features.types_list.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.navigation.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.typeListWrapper() {
    composable<Routes.TypesList> {
        BackHandler {
            // Do nothing to avoid closing the app
        }
        val viewModel: ListTypeViewModel = koinViewModel()
        val states by viewModel.states.collectAsStateWithLifecycle()

        ListTypeScreen(
            states = states,
            onEvent = viewModel::onEvent
        )
    }
}

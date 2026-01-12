package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

inline fun <reified T : Routes.TypeDetails>NavGraphBuilder.detailsTypeWrapper() {
    composable<T> {
        val viewModel = koinViewModel<DetailsTypeViewModel>()
        val states by viewModel.states.collectAsStateWithLifecycle()

        DetailsTypeScreen(
            states = states,
            onEvents = viewModel::onEvent
        )
    }
}

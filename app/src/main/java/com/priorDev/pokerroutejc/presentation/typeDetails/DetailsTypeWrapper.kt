package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

inline fun <reified T : Routes.TypeDetails>NavGraphBuilder.detailsTypeWrapper() {
    composable<T> {
        val viewModel = koinViewModel<DetailsTypeViewModel>()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val details = viewModel.details.collectAsStateWithLifecycle()
        DetailsTypeView(
            states = states.value,
            details = details.value,
            onEvents = viewModel::onEvent
        )
    }
}

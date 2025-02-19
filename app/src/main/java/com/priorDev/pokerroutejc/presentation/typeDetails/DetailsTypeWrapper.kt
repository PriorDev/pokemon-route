package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes

inline fun <reified T : Routes.TypeDetails>NavGraphBuilder.detailsTypeWrapper() {
    composable<T> {
        val viewModel: DetailsTypeViewModel = hiltViewModel()
        val states = viewModel.states.collectAsStateWithLifecycle()
        val details = viewModel.details.collectAsStateWithLifecycle()
        DetailsTypeView(
            states = states.value,
            details = details.value,
            onEvents = viewModel::onEvent
        )
    }
}

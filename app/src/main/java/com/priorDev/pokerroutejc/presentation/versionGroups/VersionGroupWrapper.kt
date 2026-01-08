package com.priorDev.pokerroutejc.presentation.versionGroups

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.versionGroupWrapper() {
    composable<Routes.VersionGroups> {
        val viewModel = koinViewModel<VersionGroupViewModel>()
        val states by viewModel.states.collectAsStateWithLifecycle()

        VersionGroupScreen(
            states = states,
            versionGroupList = viewModel.versionGroupList,
            onEvent = viewModel::onEvent
        )
    }
}
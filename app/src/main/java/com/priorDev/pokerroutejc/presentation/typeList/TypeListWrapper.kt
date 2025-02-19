package com.priorDev.pokerroutejc.presentation.typeList

import androidx.activity.compose.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.ui.Routes
import org.koin.androidx.compose.getViewModel

fun NavGraphBuilder.typeListWrapper() {
    composable<Routes.TypesList> {
        BackHandler {
            // Do nothing to avoid closing the app
        }
        val viewModel: ListTypeViewModel = getViewModel()
        val screenStates = viewModel.screenStates.collectAsStateWithLifecycle()
        val typesList = viewModel.typesList

        ListTypeView(
            screenState = screenStates.value,
            typeList = typesList,
            onEvent = viewModel::onEvent
        )
    }
}

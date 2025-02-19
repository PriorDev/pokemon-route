package com.priorDev.pokerroutejc.featureTypes.presentation.list

import androidx.activity.compose.BackHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.utils.Routes

fun NavGraphBuilder.typeListWrapper() {
    composable<Routes.TypesList> {
        BackHandler {
            // Do nothing to avoid closing the app
        }
        val viewModel: ListTypeViewModel = hiltViewModel()
        val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
        val typesList = viewModel.typesList

        ListTypeView(
            commonStates = commonStates.value,
            typeList = typesList,
            onEvent = viewModel::onEvent,
        )
    }
}

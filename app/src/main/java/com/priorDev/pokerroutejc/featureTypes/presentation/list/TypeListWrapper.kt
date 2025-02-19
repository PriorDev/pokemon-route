package com.priorDev.pokerroutejc.featureTypes.presentation.list

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.core.routes.RoutesType

fun NavGraphBuilder.typeListWrapper() {
    composable(RoutesType.TypesList.route) {
        val viewModel = hiltViewModel<ListTypeViewModel>()
        val commonStates = viewModel.commonStates.collectAsStateWithLifecycle()
        val typesList = viewModel.typesList

        ListTypeView(
            commonStates = commonStates.value,
            typeList = typesList,
            onEvent = viewModel::onEvent,
        )
    }
}

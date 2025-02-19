package com.priorDev.pokerroutejc.featureTypes.presentation.list

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.priorDev.pokerroutejc.utils.Routes

fun NavGraphBuilder.typeListWrapper() {
    composable<Routes.TypesList> {
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

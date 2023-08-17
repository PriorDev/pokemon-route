package com.prior_dev.pokerroutejc.feature_types.presentation.list

sealed class ListTypesUiEvent {
    data class openTypesDetailScreen(val typeId: Int): ListTypesUiEvent()
}

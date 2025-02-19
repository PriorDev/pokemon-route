package com.priorDev.pokerroutejc.featureTypes.presentation.list

sealed class ListTypesEvent {
    data object OnDismiss : ListTypesEvent()
}

package com.priorDev.pokerroutejc.presentation.typeList

sealed class ListTypesEvent {
    data object Refresh : ListTypesEvent()
}

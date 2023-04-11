package com.prior_dev.pokemonrroutejc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

fun<T> ViewModel.handleResource(
    result: Resource<T>,
    _states: MutableLiveData<CommonStates>,
    states: LiveData<CommonStates>,
    onSuccess: () -> Unit,
){
    when(result){
        is Resource.Error -> {
            _states.value = states.value?.copy(message = result.message ?: "")
        }
        is Resource.Loading -> {
            _states.value = states.value?.copy(isLoading = result.isLoading)
        }
        is Resource.Success -> onSuccess()
    }
}

fun String.getTypeColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this }?.color ?: EnumColorTypes.Normal.color
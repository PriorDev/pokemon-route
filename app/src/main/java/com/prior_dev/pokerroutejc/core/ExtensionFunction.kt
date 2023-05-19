package com.prior_dev.pokerroutejc.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.math.BigInteger

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


fun String.getIdFromPokeUrl(): Int{
    return this.substring(0, this.length - 1).split("/").last().toInt()
}

fun String.getBigIdFromPokeUrl(): BigInteger{
    return this.substring(0, this.length - 1).split("/").last().toBigInteger()
}

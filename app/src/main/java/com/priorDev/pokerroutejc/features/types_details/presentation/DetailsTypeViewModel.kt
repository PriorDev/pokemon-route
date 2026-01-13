package com.priorDev.pokerroutejc.features.types_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.core.utils.ResourceFlow
import com.priorDev.pokerroutejc.navigation.Routes
import com.priorDev.pokerroutejc.core.data.TypeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsTypeViewModel(
    private val repository: TypeRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _states = MutableStateFlow(DetailsTypeState())
    val states = _states.asStateFlow()

    init {
        viewModelScope.launch {
            val args = savedStateHandle.toRoute<Routes.TypeDetails>()
            repository.getTypeFlow(args.typeId)
                .collect { result ->
                    when (result) {
                        is ResourceFlow.Error -> _states.update { it.copy(uiMessages = result.uiMessages) }
                        is ResourceFlow.Loading -> {
                            _states.update { it.copy(isLoading = result.isLoading) }
                        }
                        is ResourceFlow.Success -> {
                            result.data?.let { data ->
                                _states.update { it.copy(details = data) }
                            }
                        }
                    }
                }
        }
    }

    fun onEvent(event: DetailsTypeEvents) {
        when (event) {
            DetailsTypeEvents.onDismiss -> onDismiss()
        }
    }

    fun onDismiss() {
        _states.update { it.copy(uiMessages = null) }
    }
}

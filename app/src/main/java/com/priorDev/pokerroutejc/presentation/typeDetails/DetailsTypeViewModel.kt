package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.data.TypeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsTypeViewModel(
    private val repository: TypeRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _states = MutableStateFlow(CommonStates())
    val states = _states.asStateFlow()

    private val _details = MutableStateFlow(TypeDetailsData())
    val details = _details.asStateFlow()

    init {
        viewModelScope.launch {
            val args = savedStateHandle.toRoute<Routes.TypeDetails>()
            repository.getTypeFlow(args.typeId)
                .collect { result ->
                    when (result) {
                        is ResourceFlow.Error -> _states.value = states.value.copy(uiMessages = result.uiMessages)
                        is ResourceFlow.Loading -> {
                            _states.value = states.value.copy(isLoading = result.isLoading)
                        }
                        is ResourceFlow.Success -> {
                            result.data?.let {
                                _details.value = it
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
        _states.value = states.value.copy(uiMessages = null)
    }
}

package com.priorDev.pokerroutejc.presentation.typeDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.presentation.core.LoadingIndicator
import com.priorDev.pokerroutejc.presentation.core.retryFullScreen
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.Resource
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

    private val typeId: Int

    init {
        val args = savedStateHandle.toRoute<Routes.TypeDetails>()
        typeId = args.typeId
        getType(typeId)
    }

    private fun getType(typeId: Int) {
        viewModelScope.launch {
            _states.update {
                it.copy(
                    loadingIndicator = LoadingIndicator.SolidSpinningWheel,
                    errorState = null // Clear error state on retry
                )
            }

            when (val result = repository.getType(typeId)) {
                is Resource.Error -> {
                    _states.update {
                        it.copy(
                            errorState = result.networkErrorType.retryFullScreen(
                                actionEvent = DetailsTypeEvents.OnRetryGetType
                            )
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.let { data ->
                        _states.update { it.copy(details = data) }
                    }
                }
            }

            _states.update { it.copy(loadingIndicator = LoadingIndicator.None) }
        }
    }

    fun onEvent(event: DetailsTypeEvents) {
        when(event) {
            DetailsTypeEvents.OnRetryGetType -> {
                getType(typeId)
            }
        }
    }
}

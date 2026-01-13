package com.priorDev.pokerroutejc.features.types_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.utils.ResourceFlow
import com.priorDev.pokerroutejc.core.data.TypeRepo
import com.priorDev.pokerroutejc.core.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.core.domain.types.models.TypeData
import com.priorDev.pokerroutejc.core.presentation.components.DisplayError
import com.priorDev.pokerroutejc.core.presentation.components.ErrorState
import com.priorDev.pokerroutejc.core.presentation.UiMessages
import com.priorDev.pokerroutejc.core.utils.flowSubscriber
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListTypeViewModel(
    private val repository: TypeRepo,
    private val globalEvent: GlobalEventChannel
) : ViewModel() {
    private val _states = MutableStateFlow(ListTypeStates())
    val states = _states
        .onStart {
            getAllTypes()
        }
        .flowSubscriber(initialValue = ListTypeStates())

    fun onEvent(event: ListTypesEvent) {
        when (event) {
            ListTypesEvent.Refresh -> {
                getAllTypes(isRefresh = true)
            }

            is ListTypesEvent.Navigate -> {
                globalEvent.navigate(event.route, event.navOptions)
            }
        }
    }

    private fun getAllTypes(isRefresh: Boolean = false) {
        viewModelScope.launch {
            repository.getAllTypesFlow(isRefresh)
                .collect { result ->
                    when (result) {
                        is ResourceFlow.Error -> {
                            _states.update {
                                it.copy(
                                    error = getAllTypesError(result.networkErrorType)
                                )
                            }
                        }

                        is ResourceFlow.Loading -> {
                            _states.update {
                                it.copy(
                                    loadingIndicator = result.loadingIndicator
                                )
                            }
                        }

                        is ResourceFlow.Success -> {
                            result.data?.let { data ->
                                _states.update { currentState ->
                                    currentState.copy(
                                        typeList = data
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun getAllTypesError(error: NetworkError): ErrorState {
        return when (error) {
            is NetworkError.UnableToConnect -> {
                ErrorState(
                    displayAs = DisplayError.Dialog,
                    networkError = error,
                    actionButtonText = UiMessages.StringResource(R.string.retry),
                    onAction = {
                        _states.update {
                            it.copy(error = ErrorState())
                        }
                        getAllTypes(isRefresh = true)
                    },
                    isActionButtonVisible = true,
                    dismissButtonText = UiMessages.StringResource(R.string.dismiss),
                    isDismissButtonVisible = true,
                    onDismiss = {
                        _states.update {
                            it.copy(error = ErrorState())
                        }
                    }
                )
            }

            else -> {
                ErrorState(
                    networkError = error
                )
            }
        }
    }
}

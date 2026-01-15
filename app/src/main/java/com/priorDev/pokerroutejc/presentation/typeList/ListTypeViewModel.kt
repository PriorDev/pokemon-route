package com.priorDev.pokerroutejc.presentation.typeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.presentation.core.DisplayError
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.utils.flowSubscriber
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
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

            ListTypesEvent.OnRetryGetAllTypes -> {
                _states.update { it.copy(error = null) }
                getAllTypes(isRefresh = true)
            }

            ListTypesEvent.OnDismissError -> {
                _states.update { it.copy(error = null) }
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

    private fun getAllTypesError(error: NetworkError): ErrorState<ListTypesEvent> {
        return when (error) {
            is NetworkError.UnableToConnect -> {
                ErrorState(
                    displayAs = DisplayError.Dialog,
                    networkError = error,
                    actionButtonText = UiMessages.StringResource(R.string.retry),
                    actionEvent = ListTypesEvent.OnRetryGetAllTypes,
                    isActionButtonVisible = true,
                    dismissButtonText = UiMessages.StringResource(R.string.dismiss),
                    isDismissButtonVisible = true,
                    dismissEvent = ListTypesEvent.OnDismissError
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

package com.priorDev.pokerroutejc.presentation.typeList

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.data.network.utils.NetworkError
import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.presentation.core.DisplayError
import com.priorDev.pokerroutejc.presentation.core.ErrorState
import com.priorDev.pokerroutejc.presentation.core.ScreenStates
import com.priorDev.pokerroutejc.presentation.core.UiMessages
import com.priorDev.pokerroutejc.presentation.utils.flowSubscriber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTypeViewModel @Inject constructor(
    private val repository: TypeRepo
) : ViewModel() {
    private val _typesList = mutableStateListOf<TypeData>()
    val typesList: List<TypeData> = _typesList

    private val _screenStates = MutableStateFlow(ScreenStates())
    val screenStates = _screenStates
        .onStart {
            getAllTypes()
        }
        .flowSubscriber(initialValue = ScreenStates())

    fun onEvent(event: ListTypesEvent) {
        when (event) {
            ListTypesEvent.Refresh -> {
                getAllTypes(isRefresh = true)
            }
        }
    }

    private fun getAllTypes(isRefresh: Boolean = false) {
        viewModelScope.launch {
            repository.getAllTypesFlow(isRefresh)
                .collect { result ->
                    when (result) {
                        is ResourceFlow.Error -> {
                            _screenStates.update {
                                it.copy(
                                    error = getAllTypesError(result.networkErrorType)
                                )
                            }
                        }

                        is ResourceFlow.Loading -> {
                            _screenStates.update {
                                it.copy(
                                    loadingIndicator = result.loadingIndicator
                                )
                            }
                        }

                        is ResourceFlow.Success -> {
                            result.data?.let {
                                _typesList.addAll(it)
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
                        _screenStates.update {
                            it.copy(error = ErrorState())
                        }
                        getAllTypes(isRefresh = true)
                    },
                    isActionButtonVisible = true,
                    dismissButtonText = UiMessages.StringResource(R.string.dismiss),
                    isDismissButtonVisible = true,
                    onDismiss = {
                        _screenStates.update {
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

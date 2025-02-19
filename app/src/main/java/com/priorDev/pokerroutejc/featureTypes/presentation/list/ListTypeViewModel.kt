package com.priorDev.pokerroutejc.featureTypes.presentation.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import com.priorDev.pokerroutejc.presentation.core.ScreenStates
import com.priorDev.pokerroutejc.utils.flowSubscriber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTypeViewModel @Inject constructor(
    private val repository: TypeRepository
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
                                    networkError = result.networkErrorType
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
}

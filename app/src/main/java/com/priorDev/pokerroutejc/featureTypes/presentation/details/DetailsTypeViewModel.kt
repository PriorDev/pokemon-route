package com.priorDev.pokerroutejc.featureTypes.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.core.Resource
import com.priorDev.pokerroutejc.utils.Routes
import com.priorDev.pokerroutejc.featureTypes.domain.TypeDetailsData
import com.priorDev.pokerroutejc.featureTypes.domain.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsTypeViewModel @Inject constructor(
    private val repository: TypeRepository,
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
                        is Resource.Error -> _states.value = states.value.copy(uiMessages = result.uiMessages)
                        is Resource.Loading -> {
                            _states.value = states.value.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
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

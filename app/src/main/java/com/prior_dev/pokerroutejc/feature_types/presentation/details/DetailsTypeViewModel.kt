package com.prior_dev.pokerroutejc.feature_types.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import com.prior_dev.pokerroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsTypeViewModel @Inject constructor(
    private val repository: TypeRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){
    private val _states = MutableStateFlow(CommonStates())
    val states = _states.asStateFlow()

    private val _details = MutableStateFlow(TypeDetailsData())
    val details = _details.asStateFlow()

    init {
        viewModelScope.launch {
            val typeId = savedStateHandle.get<Int>(RoutesType.TypeDetails.argType)
            typeId?.let { type ->
                repository.getTypeFlow(type)
                    .collect{ result ->
                        when(result){
                            is Resource.Error -> onDismiss()
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
    }

    fun onEvent(event: DetailsTypeEvents){
        when(event){
            DetailsTypeEvents.onDismiss -> onDismiss()
        }
    }

    fun onDismiss(){
        _states.value = states.value.copy(uiMessages = null)
    }
}
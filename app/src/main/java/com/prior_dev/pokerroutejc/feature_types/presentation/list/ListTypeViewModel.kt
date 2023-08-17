package com.prior_dev.pokerroutejc.feature_types.presentation.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTypeViewModel @Inject constructor(
    private val repository: TypeRepository
): ViewModel() {
    private val _states = MutableStateFlow(ListTypeStates())
    val states = _states.asStateFlow()

    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllTypesFlow()
                .collect{ result ->
                    when(result){
                        is Resource.Error -> {
                            _commonStates.value = commonStates.value.copy(message = result.message ?: "")
                        }
                        is Resource.Loading -> {
                            _commonStates.value = commonStates.value.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            _states.value = states.value.copy(
                                types = result.data ?: emptyList()
                            )
                        }
                    }
                }
        }
    }

    fun onEvent(event: ListTypesEvent){
        when(event){
            ListTypesEvent.onDismiss -> onDismiss()
        }
    }

    fun onDismiss(){
        _commonStates.value = commonStates.value.copy(message = "")
    }
}
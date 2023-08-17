package com.prior_dev.pokerroutejc.feature_types.presentation.details

import androidx.lifecycle.*
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import com.prior_dev.pokerroutejc.core.routes.RoutesType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsTypeViewModel @Inject constructor(
    private val repository: TypeRepository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(){
    private val _states = MutableLiveData(CommonStates())
    val states: LiveData<CommonStates> = _states

    private val _type = MutableLiveData(TypeDetailsData())
    val details: LiveData<TypeDetailsData> = _type

    init {
        viewModelScope.launch {
            val typeid = savedStateHandle.get<Int>(RoutesType.TypeDetails.argType)
            savedStateHandle.get<Int>(RoutesType.TypeDetails.argType)?.let { type ->
                repository.getTypeFlow(type)
                    .collect{ result ->
                        when(result){
                            is Resource.Error -> {
                                _states.value = states.value?.copy(message = result.message ?: "")
                            }
                            is Resource.Loading -> {
                                _states.value = states.value?.copy(isLoading = result.isLoading)
                            }
                            is Resource.Success -> {
                                _type.value = result.data
                            }
                        }
                    }
            }
        }
    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }
}
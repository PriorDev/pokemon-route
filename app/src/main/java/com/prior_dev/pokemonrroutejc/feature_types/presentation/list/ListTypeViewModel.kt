package com.prior_dev.pokemonrroutejc.feature_types.presentation.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.pokemonrroutejc.core.CommonStates
import com.prior_dev.pokemonrroutejc.core.Resource
import com.prior_dev.pokemonrroutejc.feature_types.domain.TypeData
import com.prior_dev.pokemonrroutejc.feature_types.domain.TypeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTypeViewModel @Inject constructor(
    private val repository: TypeRepository
): ViewModel() {
    private val _states = MutableLiveData(CommonStates())
    val states: LiveData<CommonStates> = _states

    private val _types = mutableStateListOf<TypeData>()
    val types: List<TypeData>  = _types

    init {
        viewModelScope.launch {
            repository.getAllTypes()
                .collect{ result ->
                    when(result){
                        is Resource.Error -> {
                            _states.value = states.value?.copy(message = result.message ?: "")
                        }
                        is Resource.Loading -> {
                            _states.value = states.value?.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            _types.clear()
                            _types.addAll(result.data ?: emptyList())
                        }
                    }
                }
        }
    }

    fun onDismiss(){
        _states.value = states.value?.copy(message = "")
    }
}
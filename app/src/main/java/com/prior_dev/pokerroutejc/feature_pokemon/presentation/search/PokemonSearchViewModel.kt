package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {
    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    private val _states = MutableStateFlow(PokemonSearchStates())
    val states = _states.asStateFlow()

    private var searchJob: Job? = null

    init {
        _commonStates.value = commonStates.value.copy(isLoading = false)
        getNextPage()
    }

    fun onEvent(event: PokemonSearchEvent){
        when(event){
            is PokemonSearchEvent.OnSearchText -> onSearchText(event.text)
            PokemonSearchEvent.getNextPage -> getNextPage()
            PokemonSearchEvent.onDismiss -> onDismiss()
        }
    }

    private fun onSearchText(text: String){
        //TODO:hacer la carga de pokemons mas suave, y arreglar la paginación
        _commonStates.value = commonStates.value.copy(searchText = text)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if(text.isBlank()){
                _states.value = states.value.copy(pokemons = emptyList())
                getNextPage()
            }else{
                repository.searchPokemonNameByMatch(text)
                    .collect{ result ->
                        when(result){
                            is Resource.Error -> showErrorDialog(result.message)
                            is Resource.Loading -> handleLoadingWheel(result.isLoading)
                            is Resource.Success -> {
                                _states.value = states.value.copy(
                                    pokemons = result.data ?: emptyList()
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun onDismiss(){
        _commonStates.value = commonStates.value.copy(message = "")
    }

    private fun getNextPage(){
        if(commonStates.value.searchText.isNotBlank()){
            return
        }

        val offset = states.value.pokemons.size

        viewModelScope.launch {
            repository.getPokemonNamePaging(offset).collect{ result ->
                when(result){
                    is Resource.Error -> showErrorDialog(result.message)
                    is Resource.Loading -> handleLoadingWheel(result.isLoading)
                    is Resource.Success -> {
                        if(offset == 0){
                            _states.value = states.value.copy(pokemons = result.data ?: emptyList())
                        }else{
                            val list = states.value.pokemons + (result.data as List<PokemonNameData>)
                            _states.value = states.value.copy(pokemons = list)
                        }
                    }
                }
            }
        }
    }

    private fun showErrorDialog(message: String?){
        _commonStates.value = commonStates.value.copy(message = message)
    }

    private fun handleLoadingWheel(isLoading: Boolean){
        _commonStates.value = commonStates.value.copy(isLoading = isLoading)
    }
}
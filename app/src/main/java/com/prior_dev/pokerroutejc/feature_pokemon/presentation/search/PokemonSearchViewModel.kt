package com.prior_dev.pokerroutejc.feature_pokemon.presentation.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prior_dev.pokerroutejc.core.CommonStates
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.UiMessages
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.presentation.utils.IGlobalEventChannel
import com.prior_dev.pokerroutejc.presentation.utils.OneTimeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonSearchViewModel @Inject constructor(
    private val repository: PokemonRepository,
): ViewModel() {
    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    private val _pokemonList = mutableStateListOf<PokemonNameData>()
    val pokemonList: List<PokemonNameData> = _pokemonList

    private var searchJob: Job? = null

    init {
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
        _commonStates.value = commonStates.value.copy(searchText = text)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if(text.isBlank()){
                _pokemonList.clear()
                getNextPage()
            }else{
                repository.searchPokemonNameByMatch(text)
                    .collect{ resource ->
                        when(resource){
                            is Resource.Error -> showErrorDialog(resource.uiMessages)
                            is Resource.Loading -> handleLoadingWheel(resource.isLoading)
                            is Resource.Success -> {
                                _pokemonList.clear()
                                _pokemonList.addAll(resource.data ?: emptyList())
                            }
                        }
                    }
            }
        }
    }

    fun onDismiss(){
        _commonStates.value = commonStates.value.copy(uiMessages = null)
    }

    private fun getNextPage(){
        //If searchTest has a value that means the user is searching for a pokemon
        //what means no pagination
        if(commonStates.value.searchText.isNotBlank()){
            return
        }

        val offset = pokemonList.size

        //One way to avoid adding repetitive pages when the job is no finish yet
        if(commonStates.value.isLoading && offset > 0)
            return

        viewModelScope.launch {
            repository.getPokemonNamePaging(offset).collect{ resource ->
                when(resource){
                    is Resource.Error -> showErrorDialog(resource.uiMessages)
                    is Resource.Loading -> handleLoadingWheel(resource.isLoading)
                    is Resource.Success -> resource.data?.let { pokemons ->
                        if(offset == 0){
                            _pokemonList.clear()
                        }
                        pokemons.map{
                            _pokemonList.add(it)
                        }
                    }
                }
            }
        }
    }

    private fun showErrorDialog(uiMessages: UiMessages){
        _commonStates.value = commonStates.value.copy(uiMessages = uiMessages)
    }

    private fun handleLoadingWheel(isLoading: Boolean){
        _commonStates.value = commonStates.value.copy(isLoading = isLoading)
    }
}
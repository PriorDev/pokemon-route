package com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.featurePokemon.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.domain.toDomain
import com.priorDev.pokerroutejc.utils.IGlobalEventChannel
import com.priorDev.pokerroutejc.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    pager: Pager<Int, PokemonNameEntity>,
    private val globalEventChannel: IGlobalEventChannel
) : ViewModel() {
    private val _commonStates = MutableStateFlow(CommonStates())
    val commonStates = _commonStates.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val pokemonList = pager
        .flow
        .map { pagingData ->
            _commonStates.update { it.copy(isLoading = false) }
            _isRefreshing.update { false }
            pagingData.map { pokemon ->
                pokemon.toDomain()
            }
        }
        .cachedIn(viewModelScope)

    private var searchJob: Job? = null

    fun onEvent(event: PokemonListEvent) {
        when (event) {
            is PokemonListEvent.OnListText -> onSearchText(event.text)

            PokemonListEvent.OnDismiss -> onDismiss()

            PokemonListEvent.OnRefresh -> onRefresh()

            PokemonListEvent.OnSearch -> {
                globalEventChannel.onNavigate(Routes.PkSearch)
            }
        }
    }

    private fun onRefresh() {
        _isRefreshing.update { true }
    }

    private fun onSearchText(text: String) {
        _commonStates.value = commonStates.value.copy(searchText = text)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            if (text.isBlank()) {
                // Execute refresh

//                _pokemonList.clear()
//                getNextPage()
            } else {
//                repository.searchPokemonNameByMatch(text)
//                    .collect{ resource ->
//                        when(resource){
//                            is Resource.Error -> showErrorDialog(resource.uiMessages)
//                            is Resource.Loading -> handleLoadingWheel(resource.isLoading)
//                            is Resource.Success -> {
//                                _pokemonList.clear()
//                                _pokemonList.addAll(resource.data ?: emptyList())
//                            }
//                        }
//                    }
            }
        }
    }

    fun onDismiss() {
        _commonStates.value = commonStates.value.copy(uiMessages = null)
    }

//    private fun getNextPage(){
//        //If searchTest has a value that means the user is searching for a pokemon
//        //what means no pagination
//        if(commonStates.value.searchText.isNotBlank()){
//            return
//        }
//
//        val offset = pokemonList.size
//
//        //One way to avoid adding repetitive pages when the job is no finish yet
//        if(commonStates.value.isLoading && offset > 0)
//            return
//
//        viewModelScope.launch {
//            repository.getPokemonNamePaging(offset).collect{ resource ->
//                when(resource){
//                    is Resource.Error -> showErrorDialog(resource.uiMessages)
//                    is Resource.Loading -> handleLoadingWheel(resource.isLoading)
//                    is Resource.Success -> resource.data?.let { pokemons ->
//                        if(offset == 0){
//                            _pokemonList.clear()
//                        }
//                        pokemons.map{
//                            _pokemonList.add(it)
//                        }
//                    }
//                }
//            }
//        }
//    }

//    private fun showErrorDialog(uiMessages: UiMessages){
//        _commonStates.value = commonStates.value.copy(uiMessages = uiMessages)
//    }
//
//    private fun handleLoadingWheel(isLoading: Boolean){
//        _commonStates.value = commonStates.value.copy(isLoading = isLoading)
//    }
}

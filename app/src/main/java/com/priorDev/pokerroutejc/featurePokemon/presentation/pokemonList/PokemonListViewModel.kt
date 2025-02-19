package com.priorDev.pokerroutejc.featurePokemon.presentation.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.featurePokemon.domain.toDomain
import com.priorDev.pokerroutejc.utils.IGlobalEventChannel
import com.priorDev.pokerroutejc.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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

    fun onEvent(event: PokemonListEvent) {
        when (event) {
            PokemonListEvent.OnDismiss -> onDismiss()

            PokemonListEvent.OnRefresh -> onRefresh()

            PokemonListEvent.OnSearch -> {
                globalEventChannel.navigate(Routes.PkSearch)
            }
        }
    }

    private fun onRefresh() {
        _isRefreshing.update { true }
    }

    fun onDismiss() {
        _commonStates.value = commonStates.value.copy(uiMessages = null)
    }
}

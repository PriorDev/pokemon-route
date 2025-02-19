package com.priorDev.pokerroutejc.presentation.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.priorDev.pokerroutejc.core.CommonStates
import com.priorDev.pokerroutejc.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.domain.pokemon.models.toDomain
import com.priorDev.pokerroutejc.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.ui.Routes

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PokemonListViewModel(
    pager: Pager<Int, PokemonNameEntity>,
    private val globalEventChannel: GlobalEventChannel
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

            is PokemonListEvent.Navigate -> {
                globalEventChannel.navigate(event.route, event.navOptions)
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

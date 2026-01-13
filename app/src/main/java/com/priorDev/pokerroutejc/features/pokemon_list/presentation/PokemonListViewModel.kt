package com.priorDev.pokerroutejc.features.pokemon_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.priorDev.pokerroutejc.core.data.database.PokemonNameEntity
import com.priorDev.pokerroutejc.core.domain.pokemon.models.toDomain
import com.priorDev.pokerroutejc.core.utils.GlobalEventChannel
import com.priorDev.pokerroutejc.navigation.Routes

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PokemonListViewModel(
    pager: Pager<Int, PokemonNameEntity>,
    private val globalEventChannel: GlobalEventChannel
) : ViewModel() {
    private val _states = MutableStateFlow(PokemonListState())
    val states = _states.asStateFlow()

    val pokemonList = pager
        .flow
        .map { pagingData ->
            _states.update { it.copy(isLoading = false, isRefreshing = false) }
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
        _states.update { it.copy(isRefreshing = true) }
    }

    fun onDismiss() {
        _states.update { it.copy(uiMessages = null) }
    }
}

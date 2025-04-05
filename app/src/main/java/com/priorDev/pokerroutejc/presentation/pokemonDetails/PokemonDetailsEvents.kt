package com.priorDev.pokerroutejc.presentation.pokemonDetails

import androidx.navigation.NavOptionsBuilder
import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.presentation.pokemonDetails.moves.MoveFilterModel
import com.priorDev.pokerroutejc.ui.Routes
import com.priorDev.pokerroutejc.utils.ApiLanguages

sealed class PokemonDetailsEvents {
    data class OnGenerationSelect(val generation: String) : PokemonDetailsEvents()

    data class ToggleMoveFilterCheck(val filter: MoveFilterModel) : PokemonDetailsEvents()

    data class ToggleLearnMethodExpand(
        val learnMethod: String,
        val isExpanded: Boolean
    ) : PokemonDetailsEvents()

    data class OnAbilityClick(val ability: String) : PokemonDetailsEvents()

    data class SelectMove(val move: MoveDetailsData?) : PokemonDetailsEvents()

    data class OnSearchTextChange(val text: String) : PokemonDetailsEvents()

    data object OnAbilityDismiss : PokemonDetailsEvents()

    data class Navigate(
        val route: Routes,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : PokemonDetailsEvents()

    data class SelectLanguage(val language: ApiLanguages) : PokemonDetailsEvents()
}

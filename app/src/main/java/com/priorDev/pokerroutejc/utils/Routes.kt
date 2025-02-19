package com.priorDev.pokerroutejc.utils

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object MainNav : Routes()

    @Serializable
    data object TypeNav : Routes()

    @Serializable
    data object PokemonNav : Routes()

    @Serializable
    data object TypesList : Routes()

    @Serializable
    open class TypeDetails(val typeId: Int) : Routes() {
        @Serializable
        data class TypeTab(val id: Int) : TypeDetails(id)

        @Serializable
        data class PokemonTab(val id: Int) : TypeDetails(id)
    }

    @Serializable
    data object PokemonList : Routes()

    @Serializable
    data object PkSearch : Routes()

    @Serializable
    data class PkDetails(val pokemonName: String) : Routes()
}

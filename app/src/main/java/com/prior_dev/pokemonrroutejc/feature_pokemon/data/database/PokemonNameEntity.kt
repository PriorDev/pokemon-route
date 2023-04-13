package com.prior_dev.pokemonrroutejc.feature_pokemon.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior_dev.pokemonrroutejc.core.getIdFromPokeUrl
import com.prior_dev.pokemonrroutejc.feature_pokemon.data.network.response.PokemonNameResponse

@Entity(tableName = "catPokemonName")
data class PokemonNameEntity (
    @PrimaryKey val id: Int,
    val name: String,
)

fun PokemonNameResponse.toDB() =
    PokemonNameEntity(
        name = name,
        id = url.getIdFromPokeUrl(),
    )
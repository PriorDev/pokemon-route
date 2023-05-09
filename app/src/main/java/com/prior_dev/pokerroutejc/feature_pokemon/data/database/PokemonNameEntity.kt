package com.prior_dev.pokerroutejc.feature_pokemon.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior_dev.pokerroutejc.core.getIdFromPokeUrl
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PokemonNameResponse

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
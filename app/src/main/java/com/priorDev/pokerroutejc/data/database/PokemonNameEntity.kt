package com.priorDev.pokerroutejc.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.data.network.pokemon.responses.PokemonNameResponse

@Entity(tableName = "PokemonNameEntity")
data class PokemonNameEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val savedTime: Long = System.currentTimeMillis()
)

fun PokemonNameResponse.toDB() =
    PokemonNameEntity(
        name = name,
        id = url.getIdFromPokeUrl()
    )

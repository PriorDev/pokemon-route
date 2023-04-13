package com.prior_dev.pokemonrroutejc.feature_types.domain

import com.prior_dev.pokemonrroutejc.core.EnumColorTypes
import com.prior_dev.pokemonrroutejc.core.getIdFromPokeUrl
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeResponse

data class TypeData(
    val id: Int,
    val name: String,
)

fun TypeResponse.toDomain() =
    TypeData(
        id = url.getIdFromPokeUrl(),
        name = name,
    )

fun TypeEntity.toDomain() =
    TypeData(
        id = id,
        name = name,
    )

fun TypeData.getColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this.name }?.color ?: EnumColorTypes.Normal.color
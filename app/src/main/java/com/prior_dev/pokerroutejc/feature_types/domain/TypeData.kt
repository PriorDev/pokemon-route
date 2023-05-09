package com.prior_dev.pokerroutejc.feature_types.domain

import com.prior_dev.pokerroutejc.core.EnumColorTypes
import com.prior_dev.pokerroutejc.core.getIdFromPokeUrl
import com.prior_dev.pokerroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeResponse

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
package com.prior_dev.pokemonrroutejc.feature_types.domain

import com.prior_dev.pokemonrroutejc.core.EnumColorTypes
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeResponse

data class TypeData(
    val name: String,
    val id: Int,
)

fun TypeResponse.toDomain() =
    TypeData(
        name = name, id = url.substring(0, url.length - 1).split("/").last().toInt()
    )

fun TypeEntity.toDomain() =
    TypeData(
        name = name, id = id
    )

fun TypeData.getColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this.name }?.color ?: EnumColorTypes.Normal.color
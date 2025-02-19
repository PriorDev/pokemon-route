package com.priorDev.pokerroutejc.domain.types.models

import com.priorDev.pokerroutejc.core.EnumColorTypes
import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.data.database.TypeEntity
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse

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
        .firstOrNull { it.type == this.name }?.color ?: EnumColorTypes.Normal.color

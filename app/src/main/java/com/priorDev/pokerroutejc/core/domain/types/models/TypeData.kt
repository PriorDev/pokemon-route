package com.priorDev.pokerroutejc.core.domain.types.models

import androidx.compose.ui.graphics.Color
import com.priorDev.GetPkMovesQuery
import com.priorDev.pokerroutejc.core.presentation.EnumColorTypes
import com.priorDev.pokerroutejc.core.utils.getIdFromPokeUrl
import com.priorDev.pokerroutejc.core.data.database.TypeEntity
import com.priorDev.pokerroutejc.core.data.network.pkType.response.TypeResponse

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

fun TypeData.getColor(): Color {
    return EnumColorTypes.entries
        .firstOrNull { it.typeId == this.id }
        ?.color
        ?: EnumColorTypes.Normal.color
}

fun GetPkMovesQuery.Pokemon_v2_type.toModel(): TypeData {
    return TypeData(
        id = id,
        name = name,
    )
}

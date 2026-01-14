package com.priorDev.pokerroutejc.domain.types.models

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.priorDev.GetPkMovesQuery
import com.priorDev.pokerroutejc.core.EnumDarkColorTypes
import com.priorDev.pokerroutejc.core.EnumLightColorTypes
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

fun GetPkMovesQuery.Pokemon_v2_type.toModel(): TypeData {
    return TypeData(
        id = id,
        name = name,
    )
}

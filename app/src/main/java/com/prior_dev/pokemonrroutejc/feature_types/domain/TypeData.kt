package com.prior_dev.pokemonrroutejc.feature_types.domain

import androidx.compose.ui.graphics.Color
import com.prior_dev.pokemonrroutejc.core.EnumColorTypes
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeResponse

data class TypeData(
    val name: String,
    val url: String,
)

fun TypeResponse.toDomain() =
    TypeData(
        name = name, url = url
    )

fun TypeEntity.toDomain() =
    TypeData(
        name = name, url = url
    )

fun TypeData.getColor() =
    EnumColorTypes.values()
        .firstOrNull{ it.type == this.name }?.color ?: EnumColorTypes.Normal.color
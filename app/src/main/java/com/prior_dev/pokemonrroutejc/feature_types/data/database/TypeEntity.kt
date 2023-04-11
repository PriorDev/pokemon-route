package com.prior_dev.pokemonrroutejc.feature_types.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeResponse

@Entity(tableName = "catType")
data class TypeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
)

fun TypeResponse.toDB() =
    TypeEntity(
        id = url.substring(0, url.length - 1).split("/").last().toInt(),
        name = name,
    )
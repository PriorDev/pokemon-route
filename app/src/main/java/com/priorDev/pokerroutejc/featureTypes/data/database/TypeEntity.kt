package com.priorDev.pokerroutejc.featureTypes.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priorDev.pokerroutejc.core.getIdFromPokeUrl
import com.priorDev.pokerroutejc.featureTypes.data.network.response.TypeResponse

@Entity(tableName = "catType")
data class TypeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
)

fun TypeResponse.toDB() =
    TypeEntity(
        id = url.getIdFromPokeUrl(),
        name = name,
    )

package com.prior_dev.pokemonrroutejc.feature_types.data.database

import androidx.room.Entity

@Entity(primaryKeys = ["type", "typeRelated"])
data class DoubleDamageFromCrossRefEntity(
    val type: String,
    val typeRelated: String,
)

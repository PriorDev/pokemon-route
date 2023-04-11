package com.prior_dev.pokemonrroutejc.feature_types.data.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.prior_dev.pokemonrroutejc.feature_types.data.database.DoubleDamageFromCrossRefEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeDetailsResponse

data class DoubleDamageFromEntity(
    @Embedded val type: TypeEntity,
    @Relation(
        parentColumn = "type",
        entityColumn = "typeRelated",
        associateBy = Junction(DoubleDamageFromCrossRefEntity::class)
    )
    val typeRelated: List<TypeEntity>
)

fun TypeDetailsResponse.toX2From(): List<DoubleDamageFromCrossRefEntity> {
    return this.damageRelationsResponse.doubleDamageFrom.map {
        DoubleDamageFromCrossRefEntity(
            this.name,
            it.name
        )
    }
}

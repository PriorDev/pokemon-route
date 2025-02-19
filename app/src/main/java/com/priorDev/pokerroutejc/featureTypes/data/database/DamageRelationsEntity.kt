package com.priorDev.pokerroutejc.featureTypes.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.priorDev.pokerroutejc.featureTypes.data.network.response.TypeDetailsResponse

@Entity(tableName = "damage_relations")
data class DamageRelationsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val ownTypeId: Int,
    val ownType: String,
    @Embedded(prefix = "double_damage_from") val doubleDamageFrom: TypeEntity? = null,
    @Embedded(prefix = "double_damage_to") val doubleDamageTo: TypeEntity? = null,
    @Embedded(prefix = "half_damage_from") val halfDamageFrom: TypeEntity? = null,
    @Embedded(prefix = "half_damage_to") val halfDamageTo: TypeEntity? = null,
    @Embedded(prefix = "no_damage_from") val noDamageFrom: TypeEntity? = null,
    @Embedded(prefix = "no_damage_to") val noDamageTo: TypeEntity? = null,
)

fun TypeDetailsResponse.toDB(): List<DamageRelationsEntity> {
    val damageRelation = mutableListOf<DamageRelationsEntity>()

    damageRelation.addAll(
        this.damageRelationsResponse.doubleDamageFrom.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                doubleDamageFrom = it.toDB()
            )
        }
    )

    damageRelation.addAll(
        this.damageRelationsResponse.doubleDamageTo.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                doubleDamageTo = it.toDB()
            )
        }
    )

    damageRelation.addAll(
        this.damageRelationsResponse.halfDamageFrom.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                halfDamageFrom = it.toDB()
            )
        }
    )

    damageRelation.addAll(
        this.damageRelationsResponse.halfDamageTo.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                halfDamageTo = it.toDB()
            )
        }
    )

    damageRelation.addAll(
        this.damageRelationsResponse.noDamageFrom.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                noDamageFrom = it.toDB()
            )
        }
    )

    damageRelation.addAll(
        this.damageRelationsResponse.noDamageTo.map {
            DamageRelationsEntity(
                ownTypeId = this.id,
                ownType = this.name,
                noDamageTo = it.toDB()
            )
        }
    )

    return damageRelation
}

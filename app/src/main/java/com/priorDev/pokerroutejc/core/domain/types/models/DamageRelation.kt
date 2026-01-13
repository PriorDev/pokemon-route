package com.priorDev.pokerroutejc.core.domain.types.models

import com.priorDev.pokerroutejc.core.data.network.pkType.response.DamageRelationsResponse

data class DamageRelation(
    val dealsDamageTo: List<DamageValue> = emptyList(),
    val takesDamageFrom: List<DamageValue> = emptyList()
)

data class DamageValue(
    val damageValue: Float,
    val type: TypeData,
)

fun DamageRelationsResponse.toDamageRelation(): DamageRelation {
    return DamageRelation(
        dealsDamageTo = buildList {
            doubleDamageTo.forEach { type ->
                add(DamageValue(2f, type.toDomain()))
            }
            halfDamageTo.forEach { type ->
                add(DamageValue(.5f, type.toDomain()))
            }
            noDamageTo.forEach { type ->
                add(DamageValue(0f, type.toDomain()))
            }
        },
        takesDamageFrom = buildList {
            doubleDamageFrom.forEach { type ->
                add(DamageValue(2f, type.toDomain()))
            }
            halfDamageFrom.forEach { type ->
                add(DamageValue(.5f, type.toDomain()))
            }
            noDamageFrom.forEach { type ->
                add(DamageValue(0f, type.toDomain()))
            }
        }
    )
}

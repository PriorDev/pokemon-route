package com.prior_dev.pokerroutejc.feature_types.domain

import com.prior_dev.pokerroutejc.feature_types.data.database.DamageRelationsEntity
import com.prior_dev.pokerroutejc.feature_types.data.network.response.DamageRelationsResponse
import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeDetailsResponse

data class TypeDetailsData(
    val id: Int = 0,
    val name: String = "",
    val damageRelationsData: DamageRelationsData = DamageRelationsData()
)

data class DamageRelationsData(
    val doubleDamageFrom: List<TypeData> = emptyList(),
    val doubleDamageTo: List<TypeData> = emptyList(),
    val halfDamageFrom: List<TypeData> = emptyList(),
    val halfDamageTo: List<TypeData> = emptyList(),
    val noDamageFrom: List<TypeData> = emptyList(),
    val noDamageTo: List<TypeData> = emptyList(),
)

fun TypeDetailsResponse.toDomain() = TypeDetailsData(
        id = id,
        name = name,
        damageRelationsData = damageRelationsResponse.toDomain()
    )

fun DamageRelationsResponse.toDomain() = DamageRelationsData(
        doubleDamageFrom = doubleDamageFrom.map { it.toDomain() },
        doubleDamageTo = doubleDamageTo.map { it.toDomain() },
        halfDamageFrom = halfDamageFrom.map { it.toDomain() },
        halfDamageTo = halfDamageTo.map { it.toDomain() },
        noDamageFrom = noDamageFrom.map { it.toDomain() },
        noDamageTo = noDamageTo.map { it.toDomain() },
    )

fun List<DamageRelationsEntity>.toDomain(): TypeDetailsData{
    val doubleDamageFrom = this
        .filter { it.doubleDamageFrom != null }
        .map { it.doubleDamageFrom!!.toDomain() }

    val doubleDamageTo = this
        .filter { it.doubleDamageTo != null }
        .map { it.doubleDamageTo!!.toDomain() }

    val halfDamageFrom = this
        .filter { it.halfDamageFrom != null }
        .map { it.halfDamageFrom!!.toDomain() }

    val halfDamageTo = this
        .filter { it.halfDamageTo != null }
        .map { it.halfDamageTo!!.toDomain() }

    val noDamageFrom = this
        .filter { it.noDamageFrom != null }
        .map { it.noDamageFrom!!.toDomain() }

    val noDamageTo = this
        .filter { it.noDamageTo != null }
        .map { it.noDamageTo!!.toDomain() }

    val damageRelationsData = DamageRelationsData(
        doubleDamageFrom = doubleDamageFrom,
        doubleDamageTo = doubleDamageTo,
        halfDamageFrom = halfDamageFrom,
        halfDamageTo = halfDamageTo,
        noDamageFrom = noDamageFrom,
        noDamageTo = noDamageTo
    )

    return TypeDetailsData(
        id = this.first().ownTypeId,
        name = this.first().ownType,
        damageRelationsData = damageRelationsData
    )
}

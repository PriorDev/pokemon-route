package com.prior_dev.pokemonrroutejc.feature_types.domain

import com.prior_dev.pokemonrroutejc.feature_types.data.database.DoubleDamageFromCrossRefEntity
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.DamageRelationsResponse
import com.prior_dev.pokemonrroutejc.feature_types.data.network.response.TypeDetailsResponse

data class TypeDetailsData(
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


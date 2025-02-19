package com.priorDev.pokerroutejc.data.network.pkType.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeDetailsResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("damage_relations") val damageRelationsResponse: DamageRelationsResponse
)

@Serializable
data class DamageRelationsResponse(
    @SerialName("double_damage_from") val doubleDamageFrom: List<TypeResponse>,
    @SerialName("double_damage_to") val doubleDamageTo: List<TypeResponse>,
    @SerialName("half_damage_from") val halfDamageFrom: List<TypeResponse>,
    @SerialName("half_damage_to") val halfDamageTo: List<TypeResponse>,
    @SerialName("no_damage_from") val noDamageFrom: List<TypeResponse>,
    @SerialName("no_damage_to") val noDamageTo: List<TypeResponse>,
)

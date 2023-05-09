package com.prior_dev.pokerroutejc.feature_types.data.network.response

import com.google.gson.annotations.SerializedName

data class TypeDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("damage_relations") val damageRelationsResponse: DamageRelationsResponse
)

data class DamageRelationsResponse(
    @SerializedName("double_damage_from") val doubleDamageFrom: List<TypeResponse>,
    @SerializedName("double_damage_to") val doubleDamageTo: List<TypeResponse>,
    @SerializedName("half_damage_from") val halfDamageFrom: List<TypeResponse>,
    @SerializedName("half_damage_to") val halfDamageTo: List<TypeResponse>,
    @SerializedName("no_damage_from") val noDamageFrom: List<TypeResponse>,
    @SerializedName("no_damage_to") val noDamageTo: List<TypeResponse>,
)

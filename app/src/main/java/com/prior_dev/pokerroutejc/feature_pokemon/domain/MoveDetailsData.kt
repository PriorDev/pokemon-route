package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.PastValueResponse
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.VersionGroupResponse
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.toDomain
import java.math.BigInteger

data class MoveDetailsData(
    var isVisible: Boolean = true,
    val name: String,
    val id: BigInteger,
    val versionGroupDetails: List<VersionGroupDetailData>,
    val accuracy: Int,
    val power: Int,
    val pp: Int,
    val priority: Int,
    val type: TypeData?,
    val damageName: String,
    val generationName: String,
    val pastValues: List<PastValueData>,
)

data class PastValueData(
    val accuracy: Int,
    val effect_chance: Any,
    val effect_entries: List<Any>,
    val power: Any,
    val pp: Any,
    val type: Any,
    val version_group: VersionGroupResponse
)

fun MoveDetailsResponse.toDomain(moveData: MoveData): MoveDetailsData{
    return MoveDetailsData(
        name = moveData.name,
        id = moveData.id,
        versionGroupDetails = moveData.versionGroupDetails,
        accuracy = accuracy ?: 0,
        power = power ?: 0,
        pp = pp ?: 0,
        priority = priority ?: 0,
        type = type?.toDomain(),
        damageName = damage_class?.name ?: "",
        generationName = generation?.name ?: "",
        pastValues = pastValues?.map {
            it.toDomain()
        } ?: emptyList(),
    )
}

fun PastValueResponse.toDomain() = PastValueData(
    accuracy = accuracy,
    effect_chance = effect_chance,
    effect_entries = effect_entries,
    power = power,
    pp = pp,
    type = type,
    version_group = version_group
)

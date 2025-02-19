package com.priorDev.pokerroutejc.featurePokemon.domain

import com.priorDev.pokerroutejc.featurePokemon.data.network.response.MoveDetailsResponse
import com.priorDev.pokerroutejc.featureTypes.domain.TypeData
import com.priorDev.pokerroutejc.featureTypes.domain.toDomain

data class MoveDetailsData(
    var isVisible: Boolean = true,
    val name: String,
    val id: Long,
    val versionGroupDetails: List<VersionGroupDetailData>,
    val accuracy: Int,
    val power: Int,
    val pp: Int,
    val priority: Int,
    val type: TypeData?,
    val damageName: String,
    val generationName: String,
    val effect: String
)

fun MoveDetailsResponse.toDomain(moveData: MoveData): MoveDetailsData {
    val effect = effectEntries.filter { it.language.name == "en" }
    var effectText = if (effect.isEmpty()) {
        ""
    } else {
        effect.first().effect
    }

    effectChance?.let {
        effectText = effectText.replace("${'$'}effect_chance", "$it")
    }

    return MoveDetailsData(
        name = moveData.name,
        id = moveData.id,
        versionGroupDetails = moveData.versionGroupDetails,
        accuracy = accuracy ?: 0,
        power = power ?: 0,
        pp = pp ?: 0,
        priority = priority ?: 0,
        type = type?.toDomain(),
        damageName = damageClass?.name ?: "",
        generationName = generation?.name ?: "",
        effect = effectText
    )
}

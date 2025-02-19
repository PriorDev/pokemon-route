package com.priorDev.pokerroutejc.domain.pokemon.models

import com.priorDev.pokerroutejc.data.network.pokemon.responses.MoveDetailsResponse
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.toDomain

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

package com.prior_dev.pokerroutejc.feature_pokemon.domain

import com.prior_dev.pokerroutejc.feature_pokemon.data.network.response.MoveDetailsResponse
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.toDomain

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

fun MoveDetailsResponse.toDomain(moveData: MoveData): MoveDetailsData{
    val effect = effect_entries.filter { it.language.name == "en" }
    var effectText = if(effect.isEmpty()){
        ""
    }else{
        effect.first().effect
    }

    effect_chance?.let {
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
        damageName = damage_class?.name ?: "",
        generationName = generation?.name ?: "",
        effect = effectText
    )
}

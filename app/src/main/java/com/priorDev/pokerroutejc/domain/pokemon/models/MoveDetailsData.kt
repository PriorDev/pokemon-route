package com.priorDev.pokerroutejc.domain.pokemon.models

import com.priorDev.GetPkMovesQuery
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.toModel
import com.priorDev.pokerroutejc.utils.orZero

data class MoveDetailsData(
    var visible: Boolean = true,
    val learnMethod: String,
    val name: String,
    val accuracy: Int,
    val power: Int,
    val pp: Int,
    val priority: Int,
    val type: TypeData?,
    val damageClass: String,
    val generationName: String,
    val effect: String,
    val machineNumber: String,
    val level: Int
)

fun GetPkMovesQuery.Pokemon_v2_pokemonmofe.toModel(): MoveDetailsData {
    return MoveDetailsData(
        learnMethod = pokemon_v2_movelearnmethod
            ?.pokemon_v2_movelearnmethodnames
            ?.firstOrNull() // filter by language
            ?.name.orEmpty(),
        name = pokemon_v2_move?.pokemon_v2_movenames?.firstOrNull()?.name.orEmpty(),
        accuracy = pokemon_v2_move?.accuracy.orZero(),
        power = pokemon_v2_move?.power.orZero(),
        pp = pokemon_v2_move?.pp.orZero(),
        priority = pokemon_v2_move?.priority.orZero(),
        type = pokemon_v2_move?.pokemon_v2_type?.toModel(),
        damageClass = pokemon_v2_move?.pokemon_v2_movedamageclass?.name.orEmpty(),
        generationName = pokemon_v2_move?.pokemon_v2_generation?.name.orEmpty(),
        effect = pokemon_v2_move?.pokemon_v2_moveeffect
            ?.pokemon_v2_moveeffecteffecttexts
            ?.firstOrNull() // filter by language
            ?.effect
            .orEmpty()
            .replaceFirst("\$effect_chance%", pokemon_v2_move?.move_effect_chance.toString()),
        machineNumber = pokemon_v2_move?.pokemon_v2_machines
            ?.firstOrNull() // filter by generations
            ?.pokemon_v2_item
            ?.pokemon_v2_itemnames
            ?.firstOrNull() // filter by language
            ?.name.orEmpty(),
        level = level
    )
}

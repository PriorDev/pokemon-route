package com.priorDev.pokerroutejc.domain.type

import com.priorDev.pokerroutejc.domain.types.models.DamageRelation
import com.priorDev.pokerroutejc.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData

val waterTypeDetails = TypeDetailsData(
    id = 11,
    name = "water",
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(5, "ground")),
            DamageValue(2f, TypeData(6, "rock")),
            DamageValue(2f, TypeData(10, "fire")),
            DamageValue(0.5f, TypeData(11, "water")),
            DamageValue(0.5f, TypeData(12, "grass")),
            DamageValue(0.5f, TypeData(16, "dragon"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(12, "grass")),
            DamageValue(2f, TypeData(13, "electric")),
            DamageValue(0.5f, TypeData(9, "steel")),
            DamageValue(0.5f, TypeData(10, "fire")),
            DamageValue(0.5f, TypeData(11, "water")),
            DamageValue(0.5f, TypeData(15, "ice"))
        )
    )
)

val dragonTypeDetails = TypeDetailsData(
    id = 16,
    name = "dragon",
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(16, "dragon")),
            DamageValue(0.5f, TypeData(9, "steel")),
            DamageValue(0f, TypeData(18, "fairy"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(15, "ice")),
            DamageValue(2f, TypeData(16, "dragon")),
            DamageValue(2f, TypeData(18, "fairy")),
            DamageValue(0.5f, TypeData(10, "fire")),
            DamageValue(0.5f, TypeData(11, "water")),
            DamageValue(0.5f, TypeData(12, "grass")),
            DamageValue(0.5f, TypeData(13, "electric"))
        )
    )
)

val fairyTypeDetails = TypeDetailsData(
    id = 18, // ID from the JSON
    name = "fairy", // Based on the ID
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(2, "fighting")),
            DamageValue(2f, TypeData(16, "dragon")),
            DamageValue(2f, TypeData(17, "dark")),
            DamageValue(0.5f, TypeData(4, "poison")),
            DamageValue(0.5f, TypeData(9, "steel")),
            DamageValue(0.5f, TypeData(10, "fire"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(4, "poison")),
            DamageValue(2f, TypeData(9, "steel")),
            DamageValue(0.5f, TypeData(2, "fighting")),
            DamageValue(0.5f, TypeData(7, "bug")),
            DamageValue(0.5f, TypeData(17, "dark")),
            DamageValue(0f, TypeData(16, "dragon"))
        )
    )
)

val ghostTypeDetails = TypeDetailsData(
    id = 8, // ID from the JSON
    name = "ghost", // Based on the ID
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(8, "ghost")),
            DamageValue(2f, TypeData(14, "psychic")),
            DamageValue(0.5f, TypeData(17, "dark")),
            DamageValue(0f, TypeData(1, "normal"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(8, "ghost")),
            DamageValue(2f, TypeData(17, "dark")),
            DamageValue(0.5f, TypeData(4, "poison")),
            DamageValue(0.5f, TypeData(7, "bug")),
            DamageValue(0f, TypeData(1, "normal")),
            DamageValue(0f, TypeData(2, "fighting"))
        )
    )
)

val groundTypeDetails = TypeDetailsData(
    id = 5, // ID from the JSON
    name = "ground", // Name from the JSON
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(4, "poison")),
            DamageValue(2f, TypeData(6, "rock")),
            DamageValue(2f, TypeData(9, "steel")),
            DamageValue(2f, TypeData(10, "fire")),
            DamageValue(2f, TypeData(13, "electric"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(11, "water")),
            DamageValue(2f, TypeData(12, "grass")),
            DamageValue(2f, TypeData(15, "ice")),
            DamageValue(0.5f, TypeData(4, "poison")),
            DamageValue(0.5f, TypeData(6, "rock")),
            DamageValue(0f, TypeData(13, "electric")),
        )
    )
)

val electricTypeDetails = TypeDetailsData(
    id = 13, // ID from the JSON
    name = "electric", // Name from the JSON
    damageRelations = DamageRelation(
        dealsDamageTo = listOf(
            DamageValue(2f, TypeData(3, "flying")),
            DamageValue(2f, TypeData(11, "water")),
            DamageValue(0.5f, TypeData(12, "grass")),
            DamageValue(0.5f, TypeData(13, "electric")),
            DamageValue(0.5f, TypeData(16, "dragon")),
            DamageValue(0f, TypeData(5, "ground"))
        ),
        takesDamageFrom = listOf(
            DamageValue(2f, TypeData(5, "ground")),
            DamageValue(0.5f, TypeData(3, "flying")),
            DamageValue(0.5f, TypeData(9, "steel")),
            DamageValue(0.5f, TypeData(13, "electric"))
        )
    )
)

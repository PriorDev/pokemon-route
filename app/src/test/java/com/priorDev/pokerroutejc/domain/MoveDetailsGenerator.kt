package com.priorDev.pokerroutejc.domain

import com.priorDev.pokerroutejc.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.domain.types.models.TypeData

fun moveTackle() = MoveDetailsData(
    learnMethod = "level-up",
    name = "tackle",
    accuracy = 100,
    power = 40,
    pp = 35,
    priority = 0,
    visible = true,
    type = TypeData(1, "normal"),
    damageClass = "physical",
    generationName = "generation-i",
    effect = "Lorem",
    machineNumber = "machine-number",
    level = 10
)

fun moveScratch() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Scratch",
    accuracy = 100,
    power = 40,
    pp = 35,
    priority = 0,
    visible = true,
    type = TypeData(1, "normal"),
    damageClass = "physical",
    generationName = "generation-i",
    effect = "Inflicts regular damage.",
    machineNumber = "",
    level = 1
)

fun moveLeer() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Leer",
    accuracy = 100,
    power = 10,
    pp = 30,
    priority = 0,
    visible = true,
    type = TypeData(1, "normal"),
    damageClass = "status",
    generationName = "generation-i",
    effect = "Lowers the target's Defense by one stage.",
    machineNumber = "",
    level = 1
)

fun moveWaterGun() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Water Gun",
    accuracy = 100,
    power = 40,
    pp = 25,
    priority = 0,
    visible = true,
    type = TypeData(11, "water"),
    damageClass = "special",
    generationName = "generation-i",
    effect = "Inflicts regular damage.",
    machineNumber = "",
    level = 6
)

fun moveBite() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Bite",
    accuracy = 100,
    power = 60,
    pp = 25,
    priority = 0,
    visible = true,
    type = TypeData(17, "dark"),
    damageClass = "physical",
    generationName = "generation-i",
    effect = "Inflicts regular damage. Has a 30% chance to make the target flinch.",
    machineNumber = "",
    level = 9
)

fun moveScaryFace() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Scary Face",
    accuracy = 100,
    power = 18,
    pp = 10,
    priority = 0,
    visible = true,
    type = TypeData(1, "normal"),
    damageClass = "status",
    generationName = "generation-ii",
    effect = "Lowers the target's Speed by two stages.",
    machineNumber = "TM06",
    level = 13
)

fun moveIceFang() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Ice Fang",
    accuracy = 95,
    power = 65,
    pp = 15,
    priority = 0,
    visible = true,
    type = TypeData(15, "ice"),
    damageClass = "physical",
    generationName = "generation-iv",
    effect = "Inflicts regular damage. Has a 10% chance to freeze the tar",
    machineNumber = "TM10",
    level = 19
)

fun moveFlail() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Flail",
    accuracy = 100,
    power = 0,
    pp = 15,
    priority = 0,
    visible = true,
    type = TypeData(1, "normal"),
    damageClass = "physical",
    generationName = "generation-ii",
    effect = "Inflicts regular damage. Power varies inversely with the user's ",
    machineNumber = "",
    level = 22
)

fun moveCrunch() = MoveDetailsData(
    learnMethod = "level-up",
    name = "Crunch",
    accuracy = 100,
    power = 80,
    pp = 15,
    priority = 0,
    visible = true,
    type = TypeData(17, "dark"),
    damageClass = "physical",
    generationName = "generation-ii",
    effect = "Inflicts regular damage. Has a 20% chance to lower the target's Defense by one stage.",
    machineNumber = "TM108",
    level = 27
)

fun moveDetailsList(): List<MoveDetailsData> {
    return listOf(
        moveTackle(),
        moveScratch(),
        moveLeer(),
        moveWaterGun(),
        moveBite(),
        moveScaryFace(),
        moveIceFang(),
        moveFlail(),
        moveCrunch()
    )
}

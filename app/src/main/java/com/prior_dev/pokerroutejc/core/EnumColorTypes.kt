package com.prior_dev.pokerroutejc.core

import androidx.compose.ui.graphics.Color
import com.prior_dev.pokerroutejc.ui.theme.*

enum class EnumColorTypes(val color: Color, val type: String = ""){
    Normal(NormalColor, "normal"),
    Fire(FireColor, "fire"),
    Fighting(FightingColor, "fighting"),
    Water(WaterColor, "water"),
    Flying(FlyingColor, "flying"),
    Grass(GrassColor, "grass"),
    Poison(PoisonColor, "poison"),
    Electric(ElectricColor, "electric"),
    Ground(GroundColor, "ground"),
    Psychic(PsychicColor, "psychic"),
    Rock(RockColor, "rock"),
    Ice(IceColor, "ice"),
    Bug(BugColor, "bug"),
    Dragon(DragonColor, "dragon"),
    Ghost(GhostColor, "ghost"),
    Dark(DarkColor, "dark"),
    Steel(SteelColor, "steel"),
    Fairy(FairyColor, "fairy"),
    Unknown(UnknownColor, "unknown"),
    Shadow(ShadowColor, "shadow");
}

package com.priorDev.pokerroutejc.core

import androidx.compose.ui.graphics.Color
import com.priorDev.pokerroutejc.ui.theme.BugColor
import com.priorDev.pokerroutejc.ui.theme.DarkColor
import com.priorDev.pokerroutejc.ui.theme.DragonColor
import com.priorDev.pokerroutejc.ui.theme.ElectricColor
import com.priorDev.pokerroutejc.ui.theme.FairyColor
import com.priorDev.pokerroutejc.ui.theme.FightingColor
import com.priorDev.pokerroutejc.ui.theme.FireColor
import com.priorDev.pokerroutejc.ui.theme.FlyingColor
import com.priorDev.pokerroutejc.ui.theme.GhostColor
import com.priorDev.pokerroutejc.ui.theme.GrassColor
import com.priorDev.pokerroutejc.ui.theme.GroundColor
import com.priorDev.pokerroutejc.ui.theme.IceColor
import com.priorDev.pokerroutejc.ui.theme.NormalColor
import com.priorDev.pokerroutejc.ui.theme.PoisonColor
import com.priorDev.pokerroutejc.ui.theme.PsychicColor
import com.priorDev.pokerroutejc.ui.theme.RockColor
import com.priorDev.pokerroutejc.ui.theme.ShadowColor
import com.priorDev.pokerroutejc.ui.theme.SteelColor
import com.priorDev.pokerroutejc.ui.theme.UnknownColor
import com.priorDev.pokerroutejc.ui.theme.WaterColor

enum class EnumColorTypes(val color: Color, val type: String = "") {
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
    Shadow(ShadowColor, "shadow")
}

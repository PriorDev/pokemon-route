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
import com.priorDev.pokerroutejc.ui.theme.StellarColor
import com.priorDev.pokerroutejc.ui.theme.UnknownColor
import com.priorDev.pokerroutejc.ui.theme.WaterColor

enum class EnumColorTypes(val color: Color, val typeId: Int) {
    Normal(NormalColor, 1),
    Fighting(FightingColor, 2),
    Flying(FlyingColor, 3),
    Poison(PoisonColor, 4),
    Ground(GroundColor, 5),
    Rock(RockColor, 6),
    Bug(BugColor, 7),
    Ghost(GhostColor, 8),
    Steel(SteelColor, 9),
    Fire(FireColor, 10),
    Water(WaterColor, 11),
    Grass(GrassColor, 12),
    Electric(ElectricColor, 13),
    Psychic(PsychicColor, 14),
    Ice(IceColor, 15),
    Dragon(DragonColor, 16),
    Dark(DarkColor, 17),
    Fairy(FairyColor, 18),
    Stellar(StellarColor, 19),
    Unknown(UnknownColor, 10001),
    Shadow(ShadowColor, 10002)
}

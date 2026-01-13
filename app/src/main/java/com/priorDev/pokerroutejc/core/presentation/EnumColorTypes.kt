package com.priorDev.pokerroutejc.core.presentation

import androidx.compose.ui.graphics.Color
import com.priorDev.pokerroutejc.core.presentation.theme.BugColor
import com.priorDev.pokerroutejc.core.presentation.theme.DarkColor
import com.priorDev.pokerroutejc.core.presentation.theme.DragonColor
import com.priorDev.pokerroutejc.core.presentation.theme.ElectricColor
import com.priorDev.pokerroutejc.core.presentation.theme.FairyColor
import com.priorDev.pokerroutejc.core.presentation.theme.FightingColor
import com.priorDev.pokerroutejc.core.presentation.theme.FireColor
import com.priorDev.pokerroutejc.core.presentation.theme.FlyingColor
import com.priorDev.pokerroutejc.core.presentation.theme.GhostColor
import com.priorDev.pokerroutejc.core.presentation.theme.GrassColor
import com.priorDev.pokerroutejc.core.presentation.theme.GroundColor
import com.priorDev.pokerroutejc.core.presentation.theme.IceColor
import com.priorDev.pokerroutejc.core.presentation.theme.NormalColor
import com.priorDev.pokerroutejc.core.presentation.theme.PoisonColor
import com.priorDev.pokerroutejc.core.presentation.theme.PsychicColor
import com.priorDev.pokerroutejc.core.presentation.theme.RockColor
import com.priorDev.pokerroutejc.core.presentation.theme.ShadowColor
import com.priorDev.pokerroutejc.core.presentation.theme.SteelColor
import com.priorDev.pokerroutejc.core.presentation.theme.StellarColor
import com.priorDev.pokerroutejc.core.presentation.theme.UnknownColor
import com.priorDev.pokerroutejc.core.presentation.theme.WaterColor

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

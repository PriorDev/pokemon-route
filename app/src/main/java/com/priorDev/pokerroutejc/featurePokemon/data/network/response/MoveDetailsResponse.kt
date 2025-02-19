package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveDetailsResponse(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    @SerialName("damage_class")
    val damageClass: DamageClassResponse?,
    @SerialName("effect_entries")
    val effectEntries: List<EffectEntryResponse>,
    @SerialName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryResponse>?,
    val generation: GenerationResponse?,
    val power: Int?,
    val pp: Int?,
    val priority: Int?,
    val type: TypeResponse?,
    @SerialName("effect_chance")
    val effectChance: Int?
)

@Serializable
data class DamageClassResponse(
    val name: String,
    val url: String
)

@Serializable
data class FlavorTextEntryResponse(
    @SerialName("flavor_text")
    val flavorText: String,
    val language: LanguageResponse,
    @SerialName("version_group")
    val versionGroup: VersionGroupResponse
)

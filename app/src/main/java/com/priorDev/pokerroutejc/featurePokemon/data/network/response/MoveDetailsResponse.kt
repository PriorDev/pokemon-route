package com.priorDev.pokerroutejc.featurePokemon.data.network.response

import com.google.gson.annotations.SerializedName
import com.priorDev.pokerroutejc.featureTypes.data.network.response.TypeResponse

data class MoveDetailsResponse(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    @SerializedName("damage_class")
    val damageClass: DamageClassResponse?,
    @SerializedName("effect_entries")
    val effectEntries: List<EffectEntryResponse>,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorTextEntryResponse>?,
    val generation: GenerationResponse?,
    val moveNamesResponses: List<MoveNamesResponse>?,
    val pastValues: List<PastValueResponse>?,
    val power: Int?,
    val pp: Int?,
    val priority: Int?,
    val type: TypeResponse?,
    @SerializedName("effect_chance")
    val effectChance: Int?
)

data class DamageClassResponse(
    val name: String,
    val url: String
)

data class FlavorTextEntryResponse(
    @SerializedName("flavor_text")
    val flavorText: String,
    val language: LanguageResponse,
    @SerializedName("version_group")
    val versionGroup: VersionGroupResponse
)

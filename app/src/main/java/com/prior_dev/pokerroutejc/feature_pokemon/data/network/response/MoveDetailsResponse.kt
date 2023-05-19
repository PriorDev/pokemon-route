package com.prior_dev.pokerroutejc.feature_pokemon.data.network.response

import com.prior_dev.pokerroutejc.feature_types.data.network.response.TypeResponse

data class MoveDetailsResponse(
    val id: Int,
    val name: String,
    val accuracy: Int?,
    val damage_class: DamageClassResponse?,
    val effect_entries: List<EffectEntryResponse>?,
    val flavor_text_entries: List<FlavorTextEntryResponse>?,
    val generation: GenerationResponse?,
    val moveNamesResponses: List<MoveNamesResponse>?,
    val pastValues: List<PastValueResponse>?,
    val power: Int?,
    val pp: Int?,
    val priority: Int?,
    val type: TypeResponse?
)

data class DamageClassResponse(
    val name: String,
    val url: String
)

data class EffectEntryResponse(
    val effect: String,
    val language: LanguageResponse,
    val short_effect: String
)

data class FlavorTextEntryResponse(
    val flavor_text: String,
    val language: LanguageResponse,
    val version_group: VersionGroupResponse
)

data class MoveNamesResponse(
    val language: LanguageResponse,
    val name: String
)

data class PastValueResponse(
    val accuracy: Int,
    val effect_chance: Any,
    val effect_entries: List<Any>,
    val power: Any,
    val pp: Any,
    val type: Any,
    val version_group: VersionGroupResponse
)
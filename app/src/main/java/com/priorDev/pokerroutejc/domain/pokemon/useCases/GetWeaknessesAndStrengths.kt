package com.priorDev.pokerroutejc.domain.pokemon.useCases

import com.priorDev.pokerroutejc.core.ResourceFlow
import com.priorDev.pokerroutejc.domain.types.models.DamageRelationsData
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.domain.types.models.TypeDetailsData
import com.priorDev.pokerroutejc.data.TypeRepo

class GetWeaknessesAndStrengths(
    private val typeRepo: TypeRepo
) {
    private var damageRelationsData: DamageRelationsData = DamageRelationsData()
    private lateinit var allTypes: List<TypeData>
    private val pokemonTypesDetails: MutableList<TypeDetailsData> = mutableListOf()

    @Suppress("CyclomaticComplexMethod")
    suspend operator fun invoke(
        pokemonTypes: List<TypeData>
    ): ResourceFlow<DamageRelationsData> {
        when (val allTypesResource = typeRepo.getAllTypes()) {
            is ResourceFlow.Error -> {
                return ResourceFlow.Error(
                    uiMessages = allTypesResource.uiMessages,
                    throwable = allTypesResource.throwable
                )
            }
            is ResourceFlow.Loading -> { }
            is ResourceFlow.Success -> allTypes = allTypesResource.data!!
        }

        pokemonTypes.forEach { type ->
            when (val typeResource = typeRepo.getType(type.id)) {
                is ResourceFlow.Error -> {
                    return ResourceFlow.Error(
                        uiMessages = typeResource.uiMessages,
                        throwable = typeResource.throwable
                    )
                }
                is ResourceFlow.Loading -> { }
                is ResourceFlow.Success -> pokemonTypesDetails.add(typeResource.data!!)
            }
        }

        if (pokemonTypesDetails.size == 1) {
            return ResourceFlow.Success(pokemonTypesDetails.first().damageRelationsData)
        }

        allTypes.forEach { enemyType ->
            val firstType = pokemonTypesDetails.first().damageRelationsData
            val secondType = pokemonTypesDetails.last().damageRelationsData

            val typeOneDamage = if (firstType.doubleDamageFrom.contains(enemyType)) {
                2f
            } else if (firstType.halfDamageFrom.contains(enemyType)) {
                .5f
            } else if (firstType.noDamageFrom.contains(enemyType)) {
                0f
            } else {
                1f
            }

            val typeTwoDamage = if (secondType.doubleDamageFrom.contains(enemyType)) {
                2f
            } else if (secondType.halfDamageFrom.contains(enemyType)) {
                .5f
            } else if (secondType.noDamageFrom.contains(enemyType)) {
                0f
            } else {
                1f
            }

            val finallyDamage = typeOneDamage.times(typeTwoDamage)

            damageRelationsData = addDamageFromRelation(
                finallyDamage = finallyDamage,
                inputDamage = damageRelationsData,
                enemyType = enemyType
            )
        }

        return ResourceFlow.Success(damageRelationsData)
    }

    private fun addDamageFromRelation(
        finallyDamage: Float,
        inputDamage: DamageRelationsData,
        enemyType: TypeData
    ): DamageRelationsData {
        var outputDamage = inputDamage

        when (finallyDamage) {
            4f -> {
                val list = inputDamage.x4DamageFrom.toMutableList()
                list.add(enemyType)
                outputDamage = inputDamage.copy(
                    x4DamageFrom = list
                )
            }

            2f -> {
                val list = inputDamage.doubleDamageFrom.toMutableList()
                list.add(enemyType)
                outputDamage = inputDamage.copy(
                    doubleDamageFrom = list
                )
            }

            .5f -> {
                val list = inputDamage.halfDamageFrom.toMutableList()
                list.add(enemyType)
                outputDamage = inputDamage.copy(
                    halfDamageFrom = list
                )
            }

            .25f -> {
                val list = inputDamage.x1_4DamageFrom.toMutableList()
                list.add(enemyType)
                outputDamage = inputDamage.copy(
                    x1_4DamageFrom = list
                )
            }

            .0f -> {
                val list = inputDamage.noDamageFrom.toMutableList()
                list.add(enemyType)
                outputDamage = inputDamage.copy(
                    noDamageFrom = list
                )
            }
        }
        return outputDamage
    }
}

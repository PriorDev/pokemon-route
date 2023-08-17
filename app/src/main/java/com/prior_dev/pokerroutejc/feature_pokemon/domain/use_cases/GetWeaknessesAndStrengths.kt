package com.prior_dev.pokerroutejc.feature_pokemon.domain.use_cases

import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.feature_types.domain.DamageRelationsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokerroutejc.feature_types.domain.TypeRepository
import javax.inject.Inject

class GetWeaknessesAndStrengths @Inject constructor(
    private val repository: TypeRepository
) {
    private var damageRelationsData: DamageRelationsData = DamageRelationsData()
    private lateinit var allTypes: List<TypeData>
    private val pokemonTypesDetails: MutableList<TypeDetailsData> = mutableListOf()

    suspend operator fun invoke(
        pokemonTypes: List<TypeData>
    ): Resource<DamageRelationsData> {
        when(val allTypesResource = repository.getAllTypes()){
            is Resource.Error -> return Resource.Error(allTypesResource.message!!)
            is Resource.Loading -> { }
            is Resource.Success -> allTypes = allTypesResource.data!!
        }

        pokemonTypes.forEach{ type ->
            when(val typeResource = repository.getType(type.id)){
                is Resource.Error -> return Resource.Error(typeResource.message!!)
                is Resource.Loading -> { }
                is Resource.Success -> pokemonTypesDetails.add(typeResource.data!!)
            }
        }

        if(pokemonTypesDetails.size == 1){
            return Resource.Success(pokemonTypesDetails.first().damageRelationsData)
        }


        allTypes.forEach { enemyType ->
            val firstType = pokemonTypesDetails.first().damageRelationsData
            val secondType = pokemonTypesDetails.last().damageRelationsData

            val typeOneDamage = if(firstType.doubleDamageFrom.contains(enemyType)){
                2f
            }else if(firstType.halfDamageFrom.contains(enemyType)) {
                .5f
            }else if(firstType.noDamageFrom.contains(enemyType)) {
                0f
            }else{
                1f
            }

            val typeTwoDamage = if(secondType.doubleDamageFrom.contains(enemyType)){
                2f
            }else (if(secondType.halfDamageFrom.contains(enemyType)) {
                .5f
            }else if(secondType.noDamageFrom.contains(enemyType)) {
                0f
            }else{
                1f
            })

            val finallyDamage = typeOneDamage.times(typeTwoDamage)

            damageRelationsData = addDamageFromRelation(
                finallyDamage = finallyDamage,
                inputDamage = damageRelationsData,
                enemyType = enemyType
            )
        }

        return Resource.Success(damageRelationsData)
    }

    private fun addDamageFromRelation(
        finallyDamage: Float,
        inputDamage: DamageRelationsData,
        enemyType: TypeData
    ): DamageRelationsData {
        var outputDamage = inputDamage

        when(finallyDamage){
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
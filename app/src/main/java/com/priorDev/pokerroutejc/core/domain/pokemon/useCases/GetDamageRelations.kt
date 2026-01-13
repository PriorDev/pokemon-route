package com.priorDev.pokerroutejc.core.domain.pokemon.useCases

import com.priorDev.pokerroutejc.core.data.TypeRepo
import com.priorDev.pokerroutejc.core.domain.types.models.DamageRelation
import com.priorDev.pokerroutejc.core.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.core.domain.types.models.TypeData
import com.priorDev.pokerroutejc.core.utils.Resource

class GetDamageRelations(
    private val typeRepo: TypeRepo
) {
    private val _damageValues = mutableListOf<DamageValue>()

    suspend operator fun invoke(
        types: List<TypeData>
    ): Resource<List<DamageValue>> {
        types.forEach { type ->
            when (val response = typeRepo.getType(type.id)) {
                is Resource.Error -> {
                    return Resource.Error(networkErrorType = response.networkErrorType)
                }

                is Resource.Success -> {
                    response.data?.damageRelations?.let {
                        updateDamageRelation(it)
                    }
                }
            }
        }

        _damageValues.removeAll { it.damageValue == 1f }

        return Resource.Success(_damageValues.toList().sortedBy { it.damageValue })
    }

    private fun updateDamageRelation(enemyDamageRelation: DamageRelation) {
        val takesDamageFrom = buildList {
            enemyDamageRelation.takesDamageFrom.forEach { enemyType ->
                val savedType = _damageValues
                    .firstOrNull { savedType ->
                        savedType.type.id == enemyType.type.id
                    }

                if (savedType != null) {
                    add(
                        DamageValue(
                            damageValue = savedType.damageValue.times(enemyType.damageValue),
                            type = savedType.type
                        )
                    )
                } else {
                    add(enemyType)
                }
            }
        }

        takesDamageFrom.forEach { enemyType ->
            val index = _damageValues.indexOfFirst { enemyType.type.id == it.type.id }
            if (index != -1) {
                _damageValues[index] = enemyType
            } else {
                _damageValues.add(enemyType)
            }
        }
    }
}

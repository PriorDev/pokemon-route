package com.priorDev.pokerroutejc.data.network.fakes

import com.priorDev.pokerroutejc.data.database.DamageRelationsEntity
import com.priorDev.pokerroutejc.data.database.TypeDao
import com.priorDev.pokerroutejc.data.database.TypeEntity

class TypeDaoFake : TypeDao {
    val typeEntities = mutableListOf<TypeEntity>()

    override suspend fun getAllTypes(): List<TypeEntity> {
        return typeEntities
    }

    override suspend fun insertTypes(types: List<TypeEntity>) {
        typeEntities.addAll(types)
    }

    override suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDamageRelation(typeId: Int) {
        TODO("Not yet implemented")
    }
}

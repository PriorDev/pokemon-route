package com.priorDev.pokerroutejc.data.database

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

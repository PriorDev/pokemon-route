package com.prior_dev.pokemonrroutejc.feature_types.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface TypeDao {
    @Query("Select * From catType")
    suspend fun getAllTypes(): List<TypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<TypeEntity>)

    @Transaction
    @Query("Select * From damage_relations where ownTypeId = :typeId")
    suspend fun getDamageRelationByTypeId(typeId: Int): List<DamageRelationsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDamageRelations(damageRelationsEntity: List<DamageRelationsEntity>)

    @Query("Delete from damage_relations where ownTypeId = :typeId")
    suspend fun deleteDamageRelation(typeId: Int)

}
package com.prior_dev.pokemonrroutejc.feature_types.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TypeDao {
    @Query("Select * From catType")
    suspend fun getAll(): List<TypeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(types: List<TypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDoubleDamageFrom(crossRef: List<DoubleDamageFromCrossRefEntity>)

    @Query("Select * From do where type = :type")
    suspend fun getDoubleDamageFrom(type: String): List<Double>
}
package com.prior_dev.pokemonrroutejc.feature_types.data

import android.accounts.NetworkErrorException
import android.util.Log
import com.prior_dev.pokemonrroutejc.core.EnumTags
import com.prior_dev.pokemonrroutejc.core.Resource
import com.prior_dev.pokemonrroutejc.core.components.SealedMyExceptions
import com.prior_dev.pokemonrroutejc.feature_types.data.database.TypeDao
import com.prior_dev.pokemonrroutejc.feature_types.data.database.toDB
import com.prior_dev.pokemonrroutejc.feature_types.data.network.TypeService
import com.prior_dev.pokemonrroutejc.feature_types.domain.TypeData
import com.prior_dev.pokemonrroutejc.feature_types.domain.TypeDetailsData
import com.prior_dev.pokemonrroutejc.feature_types.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TypeRepositoryImp @Inject constructor(
    private val service: TypeService,
    private val dao: TypeDao,
) {
    suspend fun getAllTypes(): Flow<Resource<List<TypeData>>>{
        return flow { 
            emit(Resource.Loading())
            
            val typeEntity = dao.getAll()
            if(typeEntity.isNotEmpty()){
                emit(Resource.Success(
                    typeEntity.map { it.toDomain() }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            try{
                val typeResponse = service.getAllTypes()
                typeResponse?.let { response ->
                    dao.insert(
                        response.types.map { it.toDB() }
                    )
                }

                emit(Resource.Success(
                    dao.getAll().map { it.toDomain() }
                ))
            }catch (e: Exception){
                Log.e(EnumTags.Error.tag, "getAllTypes: ${e.message}")
                emit(Resource.Error(SealedMyExceptions.serverError))
            }

            emit(Resource.Loading(false))
        }
    }

    suspend fun getType(type: String): Flow<Resource<TypeDetailsData>>{
        return flow {
            emit(Resource.Loading())

            try {
                val response = service.getType(type)

                response?.let { typeDetails ->

                    dao.insertDoubleDamageFrom()

                    emit(Resource.Success(typeDetails.toDomain()))

                } ?: emit(Resource.Error("Error al obtener los detalles del tipo"))

            }catch (ex: Exception){
                Log.e(EnumTags.Error.tag, "getType: ${ex.message}")
                emit(Resource.Error(SealedMyExceptions.serverError))
            }

            emit(Resource.Loading(false))
        }
    }
}
package com.prior_dev.pokerroutejc.feature_pokemon.data

import android.util.Log
import com.prior_dev.pokerroutejc.core.EnumTags
import com.prior_dev.pokerroutejc.core.Resource
import com.prior_dev.pokerroutejc.core.components.SealedMyExceptions
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.PokemonDao
import com.prior_dev.pokerroutejc.feature_pokemon.data.database.toDB
import com.prior_dev.pokerroutejc.feature_pokemon.data.network.PokemonService
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonNameData
import com.prior_dev.pokerroutejc.feature_pokemon.domain.PokemonRepository
import com.prior_dev.pokerroutejc.feature_pokemon.domain.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImp @Inject constructor(
    private val service: PokemonService,
    private val dao: PokemonDao,
): PokemonRepository {
    override suspend fun searchPokemonNameByMatch(name: String): Flow<Resource<List<PokemonNameData>>> {
        return flow {
            emit(Resource.Loading())

            val likeName = "%$name%"
            val pokemons = dao.getPokemonNameByMatch(likeName)

            if(pokemons.isNotEmpty()){
                emit(Resource.Success(pokemons.map { it.toDomain() }.sortedBy { it.name }))
                emit(Resource.Loading(false))
                return@flow
            }

            var existsNextPage = true
            var offset = 0
            val limit = 1281

            while(existsNextPage){
                try{
                    val response = service.getAllPokemons(
                        urlLimitOffset = "pokemon?offset=$offset&limit=$limit"
                    )

                    if(response == null){
                        emit(Resource.Error("Error al obtener los nombres de pokemon"))
                        existsNextPage = false
                    }else{
                        if(response.next == null){
                            existsNextPage = false
                        }

                        dao.insert(response.pokemons.map { it.toDB() })
                    }
                    offset += limit
                }catch (e: java.lang.Exception){
                    Log.e(EnumTags.Error.tag, "searchPokemonNameByMatch: ${e.message}")
                    emit(Resource.Error(SealedMyExceptions.serverError))
                    existsNextPage = false
                }
            }

            val pokemonDB = dao.getPokemonNameByMatch(likeName)
            emit(Resource.Success(pokemonDB.map { it.toDomain() }.sortedBy { it.name }))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getListOfPokemon(
        pokemonsNames: List<PokemonNameData>
    ): Flow<Resource<List<PokemonData>>> {
        return flow {
            emit(Resource.Loading())

            val pokemonList = mutableListOf<PokemonData>()

            try{
                pokemonsNames.forEach { pokemonName ->
                    val pokemon = service.getPokemon(pokemonName.name)

                    pokemon?.let {
                        pokemonList.add(it.toDomain())
                    }
                }

                emit(Resource.Success(pokemonList.sortedBy { it.name }))
            }catch (e: Exception){
                Log.e(EnumTags.Error.tag, "getListOfPokemon: ${e.message}" )
                emit(Resource.Error(SealedMyExceptions.serverError))
                emit(Resource.Success(pokemonList.sortedBy { it.name }))
                return@flow
            }finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getPokemon(pokemonName: String): Flow<Resource<PokemonData>> {
        return flow {
            try{
                emit(Resource.Loading())
                val pokemon = service.getPokemon(pokemonName)

                pokemon?.let {
                    emit(Resource.Success(it.toDomain()))
                } ?: emit(Resource.Error(SealedMyExceptions.serverError))

            }catch (e: Exception){
                Log.e(EnumTags.Error.tag, "getListOfPokemon: ${e.message}" )
                emit(Resource.Error(SealedMyExceptions.serverError))
            }finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getPokemonNamePaging(offSet: Int): Flow<Resource<List<PokemonNameData>>> {
        return flow {
            emit(Resource.Loading())

            try{
                val limit = 20
                val pokemonNames = service.getAllPokemons(
                    "pokemon?offset=$offSet&limit=$limit"
                )

                emit(Resource.Success(pokemonNames?.pokemons?.map { it.toDomain() }))
            }catch (e: Exception){
                Log.e(EnumTags.Error.tag, "getListOfPokemon: ${e.message}" )
                emit(Resource.Error(SealedMyExceptions.serverError))
            }

            emit(Resource.Loading(false))
        }
    }

}
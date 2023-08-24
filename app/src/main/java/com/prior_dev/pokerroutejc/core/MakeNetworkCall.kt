package com.prior_dev.pokerroutejc.core

import com.prior_dev.pokerroutejc.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MakeNetworkCall @Inject constructor() {
    suspend operator fun <T>invoke(
        call: suspend () -> T
    ): Resource<T>{
        return withContext(Dispatchers.IO){
            try{
                Resource.Success(call())
            }catch (e: HttpException){
                println(e.printStackTrace())
                Resource.Error(UiMessages.StringResource(R.string.error_trying_to_reach_remote_source))
            }catch (e: Exception){
                println(e.printStackTrace())
                Resource.Error(UiMessages.StringResource(R.string.unexpected_error))
            }
        }
    }
}
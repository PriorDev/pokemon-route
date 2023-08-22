package com.prior_dev.pokerroutejc.core

import android.util.Log
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
                Log.e(TAG, "HttpException: ${e.printStackTrace()}" )
                Resource.Error(UiMessages.StringResource(R.string.error_trying_to_reach_remote_source))
            }catch (e: Exception){
                Log.e(TAG, "Exception: ${e.printStackTrace()}" )
                Resource.Error(UiMessages.StringResource(R.string.unexpected_error))
            }
        }
    }

    companion object{
        const val TAG = "MakeNetworkCall"
    }
}
package com.priorDev.pokerroutejc.core

import com.priorDev.pokerroutejc.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class MakeNetworkCall @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) {
    @Suppress("TooGenericExceptionCaught")
    suspend operator fun <T>invoke(
        call: suspend () -> T
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.Success(call())
            } catch (e: HttpException) {
                println(e.printStackTrace())
                Resource.Error(
                    uiMessages = UiMessages.StringResource(R.string.error_trying_to_reach_remote_source),
                    throwable = e
                )
            } catch (e: Exception) {
                println(e.printStackTrace())
                Resource.Error(
                    UiMessages.StringResource(R.string.unexpected_error),
                    throwable = e
                )
            }
        }
    }
}

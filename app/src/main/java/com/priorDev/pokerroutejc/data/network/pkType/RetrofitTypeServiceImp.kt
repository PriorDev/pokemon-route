package com.priorDev.pokerroutejc.data.network.pkType

import com.priorDev.pokerroutejc.data.network.NetworkError
import com.priorDev.pokerroutejc.data.network.NetworkResource
import com.priorDev.pokerroutejc.data.network.pkType.response.TypeDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Deprecated(
    message = "Retrofit is not longer being used since I migrate to Ktor",
    replaceWith = ReplaceWith("KtorNetworkServiceImp")
)
class RetrofitTypeServiceImp @Inject constructor(
    private val api: TypeApi,
) : ITypeService {
    override suspend fun getAllTypes(): NetworkResource {
        return withContext(Dispatchers.IO) {
            NetworkResource.Fail(NetworkError.UnknownError)
        }
    }

    override suspend fun getType(typeId: Int): NetworkResource {
        return withContext(Dispatchers.IO) {
            //api.getType("type/$typeId").body()
            NetworkResource.Fail(NetworkError.UnknownError)
        }
    }
}

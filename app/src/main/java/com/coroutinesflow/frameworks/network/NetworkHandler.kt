package com.coroutinesflow.frameworks.network

import com.coroutinesflow.AppExceptions
import com.coroutinesflow.base.data.APIState
import com.coroutinesflow.base.data.BaseModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

const val TIME_OUT_IN_MILLIS_SECOND = 10000L
@ExperimentalCoroutinesApi
suspend fun <RESPONSE : BaseModel<RESPONSE>> getRemoteDate(
    iODispatcher: CoroutineDispatcher,
    function: suspend NetworkHandler<RESPONSE>. () -> Response<RESPONSE>
): Flow<APIState<RESPONSE>> = NetworkHandler<RESPONSE>().getRemoteDataAPI(function, iODispatcher)


class NetworkHandler<RESPONSE : Any> {

    private lateinit var state: APIState<RESPONSE>
    private lateinit var response: Response<RESPONSE>

    @ExperimentalCoroutinesApi
    suspend fun getRemoteDataAPI(
        function: suspend NetworkHandler<RESPONSE>.() -> Response<RESPONSE>,
        iODispatcher: CoroutineDispatcher
    ): Flow<APIState<RESPONSE>> =
        flow {
            runCatching {
                CoroutineScope(iODispatcher).launch {
                    withTimeout(TIME_OUT_IN_MILLIS_SECOND) {
                        withContext(iODispatcher) {
                            response = function.invoke(this@NetworkHandler)
                        }
                    }
                }
            }.onSuccess { job: Job ->
                job.join()
                job.invokeOnCompletion {
                    state = it?.let { notNullThrowable ->
                        APIState.ErrorState(notNullThrowable)
                    } ?: getDataOrThrowException(response)
                }
                emit(state)

            }.onFailure {
                emit(APIState.ErrorState(it))
            }
        }.catch {
            emit(APIState.ErrorState(AppExceptions.GenericErrorException))
        }.flowOn(iODispatcher)


    private fun getDataOrThrowException(it: Response<RESPONSE>): APIState<RESPONSE> {

        return it.body()?.let {
            if (it.toString().isEmpty()) {
                APIState.ErrorState(AppExceptions.HttpException)
            } else {
                APIState.DataStat(it)
            }
        } ?: APIState.ErrorState(AppExceptions.HttpException)
    }
}

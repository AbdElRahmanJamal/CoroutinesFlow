package com.coroutinesflow.frameworks.network

import com.coroutinesflow.AppExceptions
import com.coroutinesflow.base.data.APIState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class NetworkHandler<RESPONSE : Any>(private val iODispatcher: CoroutineDispatcher) {

    private val timeOutInMillisSecond = 10000L
    private val apisJobsHashMap = HashMap<String, Job>()
    private lateinit var state: APIState<RESPONSE>
    private lateinit var response: Response<RESPONSE>


    @ExperimentalCoroutinesApi
    fun callAPI(
        apiID: String,
        function: suspend () -> Response<RESPONSE>
    )=
        flow {
            runCatching {
                CoroutineScope(iODispatcher).launch {
                    withTimeout(timeOutInMillisSecond) {
                        withContext(iODispatcher) {
                            response = function()
                        }
                    }
                }
            }.onSuccess { job: Job ->
                apisJobsHashMap[apiID] = job
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
        }.flowOn(iODispatcher).distinctUntilChanged()


    private fun getDataOrThrowException(response: Response<RESPONSE>) =
        response.body()?.let {
            if (it.toString().isEmpty()) {
                APIState.ErrorState(AppExceptions.EmptyResponseException)
            } else {
                APIState.DataStat(it)
            }
        } ?: APIState.ErrorState(AppExceptions.NullResponseException)

    fun cancelJob(apiID: String): Boolean {
        return if (apisJobsHashMap.size > 0 && apisJobsHashMap.containsKey(apiID)) {
            apisJobsHashMap.getValue(apiID).cancel()
            apisJobsHashMap.getValue(apiID).isCancelled
        } else false

    }
}


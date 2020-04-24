package com.coroutinesflow

import java.io.IOException

sealed class AppExceptions : IOException() {

    object NoConnectivityException : AppExceptions()
    object EmptyResponseException : AppExceptions()
    object NullResponseException : AppExceptions()
    object GenericErrorException : AppExceptions()

}
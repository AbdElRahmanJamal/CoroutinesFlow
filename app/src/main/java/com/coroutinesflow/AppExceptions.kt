package com.coroutinesflow

import java.io.IOException

sealed class AppExceptions : IOException() {

    object NoConnectivityException : AppExceptions()
    object HttpException : AppExceptions()
    object GenericErrorException : AppExceptions()

}
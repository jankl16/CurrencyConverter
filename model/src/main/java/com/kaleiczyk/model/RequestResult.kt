package com.kaleiczyk.model

sealed class RequestResult<T>(val code: Int) {

    class Success<T>(val data: T, code: Int) : RequestResult<T>(code)

    sealed class Error<T>(
        code: Int,
        val message: String,
        val exception: Exception?,
    ) : RequestResult<T>(code) {

        class Global<T>(
            message: String,
            code: Int,
            exception: Exception?,
        ) : Error<T>(code, message, exception = exception)

        class ServerError<T>(
            message: String,
            code: Int,
        ) : Error<T>(code, message, null)

        class LocalError<T>(
            message: String,
            code: Int,
            exception: Exception?,
        ) : Error<T>(code, message, exception)

        class Network<T> : Error<T>(code = -1, message = "", exception = null)

        class UnknownHttp<T>(
            message: String,
            code: Int
        ) : Error<T>(code, message, null)
    }
}

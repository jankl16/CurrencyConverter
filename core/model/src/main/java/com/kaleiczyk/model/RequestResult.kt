package com.kaleiczyk.model

sealed class RequestResult<T>(val code: Int) {

    class Success<T>(val data: T, code: Int) : RequestResult<T>(code)

    sealed class Error<T>(
        code: Int,
        val message: String,
        val title: String,
        val exception: Exception?,
    ) : RequestResult<T>(code) {

        class ServerError<T>(
            message: String,
            code: Int,
        ) : Error<T>(code, message, title = "Server Error", null)

        class LocalError<T>(
            message: String,
            code: Int,
            title: String = "",
            exception: Exception? = null,
        ) : Error<T>(code, message, title, exception)

        class Network<T> : Error<T>(
            code = -1,
            title = "No network",
            message = "Check your internet connection",
            exception = null
        )

        class UnknownHttp<T>(
            message: String,
            code: Int
        ) : Error<T>(code, message, title = "Unknown Error", null)
    }
}

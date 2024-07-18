package com.kaleiczyk.network.utils

import com.kaleiczyk.model.RequestResult
import retrofit2.Response
import java.io.IOException

inline fun <T, R> sendRetrofitRequest(
    request: () -> Response<T>,
    mapper: (T) -> R
): RequestResult<R> {
    val result = sendRetrofitRequest(request)
    with(result) {
        return when (this) {
            is RequestResult.Success -> RequestResult.Success(mapper(data), code)
            is RequestResult.Error.Network -> RequestResult.Error.Network()
            is RequestResult.Error.LocalError -> RequestResult.Error.LocalError(
                message = message,
                code = code,
                title = title,
                exception = exception
            )

            is RequestResult.Error.ServerError -> RequestResult.Error.ServerError(message, code)
            is RequestResult.Error.UnknownHttp -> RequestResult.Error.UnknownHttp(message, code)
        }
    }
}

inline fun <T> sendRetrofitRequest(request: () -> Response<T>): RequestResult<T> {
    try {
        val result = request()
        with(result) {
            val body = body()
            return when {
                isSuccessful && body != null -> RequestResult.Success(
                    body,
                    code()
                )
                code() in 400..499 -> RequestResult.Error.LocalError(message(), code())

                code() in 500..599 -> RequestResult.Error.ServerError(
                    message(),
                    code()
                )

                else -> RequestResult.Error.UnknownHttp(message(), code())
            }
        }
    } catch (e: IOException) {
        return RequestResult.Error.Network()
    }
}

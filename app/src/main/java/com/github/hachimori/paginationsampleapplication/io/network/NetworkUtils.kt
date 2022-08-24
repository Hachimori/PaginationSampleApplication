package com.github.hachimori.paginationsampleapplication.io.network

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

fun <T> Response<T>.getBodyOrThrow(): T {
    val body = body()
    if (!isSuccessful || body == null) {
        Timber.e("Bad response from API %s", code())
        throw IOException(code().toString())
    }
    return body
}

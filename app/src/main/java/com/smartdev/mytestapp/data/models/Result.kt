package com.smartdev.mytestapp.data.models
import retrofit2.Response

suspend fun <T> request(
    api: suspend () -> Response<T>
): ApiResponse {
    return try {
        val r = api()
        val data = r.body()
        if (r.isSuccessful) {
            ApiResponse.Result(data)
        } else {
            ApiResponse.Error("Ошибка", r.message())
        }
    } catch (e: Exception) {
        ApiResponse.Error("Ошибка", e.toString())
    }
}

sealed class ApiResponse {
    data class Error(val msg: String, val desc: String): ApiResponse()
    data class Result<out T>(val data: T): ApiResponse()
}
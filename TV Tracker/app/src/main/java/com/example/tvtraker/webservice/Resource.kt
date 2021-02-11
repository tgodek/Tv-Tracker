package com.example.tvtraker.webservice

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorMessage: String?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}
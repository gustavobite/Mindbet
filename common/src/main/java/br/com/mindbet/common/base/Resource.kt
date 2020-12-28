package br.com.mindbet.common.base

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class Resource<T> private constructor(val status: Status, val data: T? = null, val exception: Throwable? = null) {

    enum class Status {
        SUCCESS, CACHE, ERROR, LOADING
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }
        fun <T> cache(data: T?): Resource<T> {
            return Resource(Status.CACHE, data)
        }
        fun <T> error(exception: Throwable?, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                exception
            )
        }
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data
            )
        }
    }

    fun <T> transform(data: T? = null) : Resource<T> {
        return Resource(status, data, exception)
    }

}

fun <T> Exception.toResourceFlow():Flow<Resource<T>> = flow { emit(Resource.error<T>(this@toResourceFlow))  }

package br.com.mindbet.common.extension.koin


import br.com.mindbet.common.BuildConfig
import org.koin.core.scope.Scope
import retrofit2.Retrofit


inline fun <reified T> Scope.resolveRetrofit(): T? {
    if (BuildConfig.IS_MOCK) return null
    val retrofit: Retrofit = get()
    return retrofit.create(T::class.java)
}
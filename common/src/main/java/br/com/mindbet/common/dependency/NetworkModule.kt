package br.com.mindbet.common.dependency

import br.com.mindbet.common.BuildConfig
import br.com.mindbet.common.environment.EnvironmentConfig
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {
    companion object {
        val dependencyModule = module {
            single {
                provideOkHttpClient()
            }
            single { provideRetrofit(get()) }
        }

        private fun provideOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .followRedirects(true)
            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    redactHeader("Authorization")
                })
            }
            return builder.build()
        }


        private fun provideRetrofit(
            client: OkHttpClient
        ): Retrofit = Retrofit.Builder()
            .baseUrl("http://mindbetapi.azurewebsites.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}

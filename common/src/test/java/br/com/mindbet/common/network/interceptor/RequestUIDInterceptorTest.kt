package br.com.mindbet.common.network.interceptor

import com.nhaarman.mockitokotlin2.anyOrNull
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class RequestUIDInterceptorTest : AutoCloseKoinTest() {


    private val chainMock = Mockito.mock(Interceptor.Chain::class.java) as Interceptor.Chain

    private val interceptor by lazy { RequestUIDInterceptor }

    @Test
    fun intercept() {
        val request = getRequest()

        `when`(chainMock.request()).thenReturn(request)
        `when`(chainMock.proceed(anyOrNull())).then {
            val newRequest = it.arguments[0] as Request
            Assert.assertNotNull(
                "Should add Request-UID header",
                newRequest.header("Request-UID")
            )
            getResponse(newRequest)
        }

        interceptor.intercept(chainMock)

    }


    private fun getRequest() = Request.Builder()
        .url("http://teste.original.com.br")
        .build()

    private fun getResponse(request: Request) = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(200)
        .message("{ \"result\": \"ok\" }")
        .build()

}
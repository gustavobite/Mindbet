package br.com.mindbet.common.network.interceptor

import br.com.mindbet.common.authentication.AccessToken
import br.com.mindbet.common.authentication.AuthenticationManager
import com.nhaarman.mockitokotlin2.anyOrNull
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.koin.test.mock.declare
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

class AuthenticationInterceptorTest : AutoCloseKoinTest() {


	private val mockAuthenticationManager = Mockito.mock(AuthenticationManager::class.java) as AuthenticationManager
	private val chainMock = Mockito.mock(Interceptor.Chain::class.java) as Interceptor.Chain

	private val interceptor by lazy { AuthenticationInterceptor(get()) }

	init {
		startKoin {  }
		declare { single { mockAuthenticationManager } }
	}

	private val validAccessToken = AccessToken(
		idToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cmlUZWNoPVBFUlNPTkFAMTU1MzU5IiwiaWF0IjoxNTYzODI4NTYxLCJpc3MiOiJCQU5DTyBPUklHSU5BTCIsImV4cCI6MTU2MzkxNDk2MSwibmFtZSI6ImN1c3RvbWVyIiwiaWRlbnRpZmljYXRpb25OdW1iZXIiOiIxNTQwNDUxNjU4NSJ9.pbfNTV4xZHizyEiHXQSf6UvXFT9CH5qg6qhEdI-e8vE",
		accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cmlUZWNoPVBFUlNPTkFAMTU1MzU5IiwiaWF0IjoxNTYzODI4NTYxLCJpc3MiOiJCQU5DTyBPUklHSU5BTCIsImV4cCI6MTU2MzkxNDk2MSwibmFtZSI6ImN1c3RvbWVyIiwiaWRlbnRpZmljYXRpb25OdW1iZXIiOiIxNTQwNDUxNjU4NSJ9.pbfNTV4xZHizyEiHXQSf6UvXFT9CH5qg6qhEdI-e8vE",
		refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cmlUZWNoPVBFUlNPTkFAMTU1MzU5IiwiaWF0IjoxNTYzODI4NTYxLCJpc3MiOiJCQU5DTyBPUklHSU5BTCIsImV4cCI6MTU2MzkxNDk2MSwibmFtZSI6ImN1c3RvbWVyIiwiaWRlbnRpZmljYXRpb25OdW1iZXIiOiIxNTQwNDUxNjU4NSJ9.pbfNTV4xZHizyEiHXQSf6UvXFT9CH5qg6qhEdI-e8vE",
		expiresIn = Date().time.plus(24*60*60*1000)
	)

	@Test
	fun intercept_withoutAccessToken() {
		val request = getRequest()

		`when`(chainMock.request()).thenReturn(request)
		`when`(chainMock.proceed(anyOrNull())).thenReturn(getResponse(request))

		interceptor.intercept(chainMock)

		Assert.assertNull("Should not add any authorization header", request.header("Authorization"))
	}

	@Test
	fun intercept_withAccessToken() {
		val request = getRequest()

		`when`(chainMock.request()).thenReturn(request)
		`when`(chainMock.proceed(anyOrNull())).then {
			val newRequest = it.arguments[0] as Request
			Assert.assertNotNull("Should add authorization header", newRequest.header("Authorization"))
			Assert.assertEquals("Should add authorization header", "${validAccessToken.type} ${validAccessToken.accessToken}", newRequest.header("Authorization"))
			getResponse(newRequest)
		}

		`when`(mockAuthenticationManager.getAccessToken()).thenReturn(validAccessToken)
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
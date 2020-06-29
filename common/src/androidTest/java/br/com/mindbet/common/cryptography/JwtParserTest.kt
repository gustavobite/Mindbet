package br.com.mindbet.common.cryptography

import androidx.test.runner.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JwtParserTest {

	@Test
	fun decoded_withInvalidJwt() {
		assertNull("Should return null", JwtParser.decoded(""))
	}

	@Test
	fun decoded_withValidJwt() {
		val decoded = JwtParser.decoded("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cmlUZWNoPVBFUlNPTkFAMTU1MzU5IiwiaWF0IjoxNTYzODI4NTYxLCJpc3MiOiJCQU5DTyBPUklHSU5BTCIsImV4cCI6MTU2MzkxNDk2MSwibmFtZSI6ImN1c3RvbWVyIiwiaWRlbnRpZmljYXRpb25OdW1iZXIiOiIxNTQwNDUxNjU4NSJ9.pbfNTV4xZHizyEiHXQSf6UvXFT9CH5qg6qhEdI-e8vE")
		assertNotNull("Should not return null", decoded)
	}
}
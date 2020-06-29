package br.com.mindbet.common.cryptography

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonObject

object JwtParser {
	fun decoded(token: String) : JsonObject? {
		val split = token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
		return if (split.size == 3) {
			val payload = split[1]
			val decoded = String(Base64.decode(payload, Base64.DEFAULT), Charsets.UTF_8)
			val gson = Gson()
			try {
				gson.fromJson(decoded, JsonObject::class.java)
			}
			catch (e: Throwable) {
				null
			}

		}
		else {
			null
		}

	}

	private fun getJson(strEncoded: String): String {
		val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
		return String(decodedBytes, Charsets.UTF_8)
	}

}
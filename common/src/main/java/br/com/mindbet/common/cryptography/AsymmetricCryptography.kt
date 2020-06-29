package br.com.mindbet.common.cryptography

import android.util.Base64

import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


object AsymmetricCryptography {

	private const val base64Flag = Base64.NO_WRAP
	private const val CRYPTO_BITS = 2048
	private const val ALGORITHM = "RSA"
	private fun getCipher() = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")

	fun generateKeyPair(): KeyPair {

		val generator = KeyPairGenerator.getInstance(ALGORITHM)
		generator.initialize(CRYPTO_BITS)

		return generator.genKeyPair()
	}

	private fun publicKeyFromString(value: String): PublicKey? {
		return try {
			val bytes = Base64.decode(value, base64Flag)
			val spec = X509EncodedKeySpec(bytes)
			val keyFactory = KeyFactory.getInstance(ALGORITHM)

			keyFactory.generatePublic(spec)
		} catch (e: Throwable) {
			null
		}
	}

	private fun publicKeyFromByteArray(value: ByteArray): PublicKey? {
		return try {
			val bytes = Base64.decode(value, base64Flag)
			val spec = X509EncodedKeySpec(bytes)
			val keyFactory = KeyFactory.getInstance(ALGORITHM)

			keyFactory.generatePublic(spec)
		} catch (e: Throwable) {
			null
		}
	}

	private fun privateKeyFromString(value: String): PrivateKey? {
		return try {
			val keyBytes: ByteArray = Base64.decode(value, base64Flag)
			val spec = PKCS8EncodedKeySpec(keyBytes)
			val keyFactory = KeyFactory.getInstance(ALGORITHM)

			keyFactory.generatePrivate(spec)

		} catch (e: Throwable) {
			null
		}
	}

	fun encrypt(value: String, publicKey: String): String? = encrypt(value, publicKeyFromString(publicKey))
	fun encrypt(value: String, publicKey: ByteArray): String? = encrypt(value, publicKeyFromByteArray(publicKey))
	fun encrypt(value: String, publicKey: PublicKey?): String? {

		if (publicKey == null) return null

		val encryptedBytes: ByteArray
		val pubKey: PublicKey? = publicKey
		val cipher = getCipher()

		cipher.init(Cipher.ENCRYPT_MODE, pubKey)
		encryptedBytes = cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8))

		return Base64.encodeToString(encryptedBytes, base64Flag)
	}

	fun decrypt(value: String, privateKey: String): String? = decrypt(value, privateKeyFromString(privateKey))
	fun decrypt(value: String, privateKey: PrivateKey?): String? {

		if (privateKey == null) return null

		val decryptedBytes: ByteArray
		val key: PrivateKey? = privateKey
		val cipher = getCipher()

		cipher.init(Cipher.DECRYPT_MODE, key)
		decryptedBytes = cipher.doFinal(Base64.decode(value, base64Flag))

		return String(decryptedBytes)
	}
}

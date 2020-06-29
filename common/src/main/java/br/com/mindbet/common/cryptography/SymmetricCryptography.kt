package br.com.mindbet.common.cryptography

import android.util.Base64

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object SymmetricCryptography {
    private const val base64Flag = Base64.NO_WRAP
    private fun getCipher() = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    private fun getCipherPass() = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    private fun getSecretKeySepc(key: String) =
        SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "AES")

    private val keyV2 = PublicKeyHelper.keySymmetric

    fun encrypt(value: String, key: String): String {
        val skeySpec = getSecretKeySepc(key)
        val cipher = getCipher()

        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        val randomIvSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, randomIvSpec, SecureRandom())
        val encrypted = cipher.doFinal(value.toByteArray())

        val byteBuffer = ByteBuffer.allocate(iv.size + encrypted.size)
        byteBuffer.put(iv)
        byteBuffer.put(encrypted)

        val cipherMessage = byteBuffer.array()
        return Base64.encodeToString(cipherMessage, base64Flag)
    }

    fun decrypt(encrypted: String, key: String): String {

        val skeySpec = getSecretKeySepc(key)
        val cipher = getCipher()
        val iv = ByteArray(cipher.blockSize)
        val byteBuffer = ByteBuffer.wrap(Base64.decode(encrypted, base64Flag))

        byteBuffer.get(iv)

        val cipherText = ByteArray(byteBuffer.remaining())
        byteBuffer.get(cipherText)

        cipher.init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(iv))
        val plainText = cipher.doFinal(cipherText)

        return String(plainText)
    }

    fun encryptV2(data: String): String? {
        return try {
            val cipher = getCipher()
            val ivStr = randomText(16)
            val initialBytes = ivStr.toByteArray()

            val iv = IvParameterSpec(initialBytes)
            val skeySpec = SecretKeySpec(keyV2, "AES")

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val dataEncrypted = cipher.doFinal(data.toByteArray())

            ivStr + Base64.encodeToString(dataEncrypted, Base64.NO_WRAP)
        } catch (ex: Exception) {
            null
        }
    }

    fun decryptV2(encrypted: String): String? {
        return try {
            val cipher = getCipher()
            val initVector = encrypted.substring(0, 16)
            val message = encrypted.substring(16, encrypted.length)

            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(keyV2, "AES")

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(message, Base64.DEFAULT))

            String(original)
        } catch (ex: Exception) {
            null
        }
    }

    fun encryptPassword(value: String, key: String): String {
        val skeySpec = getSecretKeySepc(key)
        val cipher = getCipherPass()

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)

        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.encodeToString(encrypted, base64Flag)
    }

    private fun randomText(length: Int): String {
        val ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/+"
        val rnd = SecureRandom()

        val sb = StringBuilder(length)
        for (i in 0 until length)
            sb.append(ab[rnd.nextInt(ab.length)])
        return sb.toString()
    }
}
package br.com.mindbet.common.helper

import android.util.Log
import br.com.mindbet.common.cryptography.SymmetricCryptography
import com.google.gson.Gson


/**
 * Created by Luan Gabriel on 17/09/19.
 */

class EncryptHelper {

    class InvalidEncryptException : Exception()
    class InvalidDecryptException : Exception()

    fun <T> encrypt(data: T, aesKey: String): String? {
        return try {
            SymmetricCryptography.encrypt(Gson().toJson(data), aesKey)
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message)
            throw InvalidEncryptException()
        }
    }

    fun <T> decrypt(encrypted: String, typeOf: Class<T>, aesKey: String): T? {
        return try {
            val data = SymmetricCryptography.decrypt(encrypted, aesKey)
            Gson().fromJson<T>(data, typeOf)
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message)
            throw InvalidDecryptException()

        }
    }

    fun decrypt(encrypted: String, aesKey: String): String? {
        return try {
            SymmetricCryptography.decrypt(encrypted, aesKey)
        } catch (e: Exception) {
            Log.e(this.javaClass.name, e.message)
            throw InvalidDecryptException()
        }
    }


}
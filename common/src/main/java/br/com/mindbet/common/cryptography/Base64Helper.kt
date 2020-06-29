package br.com.mindbet.common.cryptography

import android.util.Base64

object Base64Helper {


    fun toBase64(bytes: ByteArray, flag: Int = Base64.NO_WRAP): String {
        return Base64.encodeToString(bytes, flag)
    }

    fun fromBase64(base64: String, flag: Int = Base64.NO_WRAP): ByteArray {
        return Base64.decode(base64, flag)
    }
}